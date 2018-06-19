package server.manager;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.json.JSONObject;
import server.*;
import server.mapper.SlaveMapper;
import server.simpleclass.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class CustomerManager implements Observer {
    private static CustomerManager customerManager = null;

    // 当前连接在服务器上的用户的map, key为socket, value为room_id
    private Map<Socket, String> customerMap = new HashMap<>();
    private Map<Socket, Long> sockets = new HashMap<>();
    private int querySeq = 0;
    private int billSeq = 0;
    private long keepAliveInterval = 60;



    private CustomerManager(){
    }

    public static CustomerManager getInstance() {
        if (customerManager == null) customerManager = new CustomerManager();
        return customerManager;
    }

    /*
     * login 处理登录信息, 给从机返回结果
     * @param customer 登录的用户
     * @param socket 用户的socket
     */
    public void login(Customer customer, Socket socket) {
        Processor.getInstance().runTask(new Runnable() {
            @Override
            public void run() {
                PrintWriter printWriter = null;
                try {
                    printWriter = new PrintWriter(socket.getOutputStream());
                    String ack;
                    if (isRegistered(customer)) {
                        Socket s = getSocketKey(customer.getRoom_id());
                        if (s == null)
                            customerMap.put(socket, customer.getRoom_id());
                        else {
                            customerMap.remove(s);
                            s.close();
                            customerMap.put(socket, customer.getRoom_id());
                        }
                        ack = String.format(
                                "{\"type\":\"login_ack\",\"result\":\"succeed\",\"mode\":\"%s\"}",
                                TCPServer.getInstance().getMode());
                        // 添加账单
                        BillManager.getInstance().addBill(customer.getRoom_id());
                        TCPServer.getInstance().sendData(socket,"0031{\"type\":\"state_query\",\"seq\":0}");
                        // 记录从机开机时间
                        Slave slave = new Slave(
                                customer.getRoom_id(),
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        );
                        // 添加状态
                        StateManager.getInstance().addRoomState(new RoomState(customer.getRoom_id()));
                        addSocket(socket);
                        try {
                            new SlaveMapper().insert(slave);
                        } catch (SQLServerException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Welcome customer " + customer.getId() + " in room " + customer.getRoom_id());
                        System.out.println("Login succeeded!");
                    } else {
                        ack = String.format(
                                "{\"type\":\"login_ack\",\"result\":\"failed\",\"mode\":\"%s\"}",
                                TCPServer.getInstance().getMode());
                        System.out.println("Login failed!");
                    }
                    ack = StringUtils.getHead(ack.length()) + ack;
                    printWriter.print(ack);
                    printWriter.flush();
//                    printWriter.print("0031{\"type\":\"state_query\",\"seq\":0}");
//                    printWriter.flush();
                    System.out.println(ack);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }

    /*
     * @Description isRegistered 判断用户是否登记, 房间号和身份证号是否对应
     * @param customer 待判断的用户
     * @return boolean 用户是否在数据库中, room_id 和 id 是否对应
     */
    private boolean isRegistered(Customer customer) {
        String roomId = customer.getRoom_id();
        String id = customer.getId();
        ArrayList<HashMap<String, String>> result = DataBaseConnect.
                query("select id from customer where room_id = " + "'" + roomId + "'");
        if (result != null && result.size() != 0 && id.equals(result.get(0).get("id")))
                return true;
        return false;
    }

    public String getRoomId(Socket socket) {
        return customerMap.get(socket);
    }

    private Socket getSocketKey(String room_id) {
        for (Socket s : customerMap.keySet()) {
            if (customerMap.get(s).equals(room_id)) return s;
        }
        return null;
    }

    public String removeCustomer(Socket socket) {
        String room_id = customerMap.remove(socket);
        StateManager.getInstance().removeRoom(room_id);
        return room_id;
    }

    private void broadCast(String query) {
        for (Socket socket : customerMap.keySet()) {
            TCPServer.getInstance().sendData(socket, query);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //处理 server.TimerSubject 的消息, 有query消息和bill消息
        Runnable task = new Runnable() {
            @Override
            public void run() {
                String type = (String)arg;
                if ("query".equals(type) && customerMap.size() > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "state_query");
                    map.put("seq", querySeq++);
                    String query = new JSONObject(map).toString();
                    broadCast(query);
                    System.out.println("Send state query info");
                }
                if ("send_bill".equals(type) && customerMap.size() > 0) {
                    HashMap<String, Bill> billMap = BillManager.getInstance().getBillMap();
                    for (Socket s : customerMap.keySet()) {
                        Bill bill = billMap.get(customerMap.get(s));
                        if (bill == null) continue;
                        float power = bill.getElectricity();
                        float cost = bill.getCost();
                        Map<String, Object> map = new HashMap<>();
                        map.put("type", "bill");
                        map.put("seq", billSeq);
                        map.put("power", power);
                        map.put("money", cost);
                        String json = new JSONObject(map).toString();
                        TCPServer.getInstance().sendData(s, json);
                    }
                    billSeq++;
                    System.out.println("Send bill info");
                }
                if ("check_alive".equals(type)) checkAlive();
            }
        };

        Processor.getInstance().runTask(task);
    }

    /*
     * @Description getCustomerMap 返回用户的map, key为socket, value为room_id
     * @Param
     * @Return Map<Socket, String>
     */
    public Map<Socket, String> getCustomerMap() {
        return customerMap;
    }

    /*
     * @Description check 用户结账
     * @Param room_id 房间号
     * @Return boolean socket是否断开成功
     */
    public boolean check(String room_id) {
        boolean result = true;
        Socket socket = getSocketKey(room_id);
        // socket还没有断开
        if (socket != null) {
            RequestManager manager = RequestManager.getInstance();
            Request request = manager.getRequest(room_id);
            // 温控请求还没有完成
            if (request != null) {
                // 结束温控请求
                manager.removeRequest(room_id);
                String checkJson = "{\"type\":\"check\"}";
                TCPServer.getInstance().sendData(socket, checkJson);
            }
            // 断开socket
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }
        // 移除账单
        BillManager.getInstance().remove(room_id);
        // 移除状态信息
        StateManager.getInstance().removeRoom(room_id);
        checkoutDatabase(room_id);
        return result;
    }

    private synchronized void checkAlive() {
        long current = System.currentTimeMillis();
        for (Socket s : sockets.keySet()) {
            long interval = (current - sockets.get(s).longValue()) / 1000;
            if (interval > keepAliveInterval) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sockets.remove(s);
                TCPServer.getInstance().removeSocket(s);
            }
        }
    }

    private synchronized void addSocket(Socket socket) {
        sockets.put(socket, Long.valueOf(System.currentTimeMillis()));
    }

    public boolean Register(Customer customer) {
        String room_id = customer.getRoom_id();
        Connection connection = DataBaseConnect.getConnection();
        if (connection != null) {
            Statement statement = null;
            ResultSet res = null;
            int is_booked = 1;
            try {
                statement = connection.createStatement();
                res = statement.executeQuery(
                        String.format(
                                "select is_booked from room where room_id = '%s'", room_id
                        ));
                if (res.next()) {
                    is_booked = res.getInt("is_booked");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (res != null) res.close();
                    if (statement != null) statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (is_booked == 1) return false;
        }
        boolean result = false;
        connection = DataBaseConnect.getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            connection.setAutoCommit(false);
            String sql1 = String.format(
                    "update room set is_booked = 1 where room_id = '%s'",
                    room_id
            );
            String sql2 = String.format(
                    "insert customer(room_id, id) " +
                            "values('%s', %s)",
                    customer.getRoom_id(), customer.getId()
            );
            ps1 = connection.prepareStatement(sql1);
            ps1.execute();
            ps2 = connection.prepareStatement(sql2);
            ps2.execute();
            connection.commit();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<String> getAvailableRoom() {
        Connection connection = DataBaseConnect.getConnection();
        List<String> availableRoomList = null;
        if (connection != null) {
            Statement statement = null;
            ResultSet res = null;
            try {
                statement = connection.createStatement();
                res = statement.executeQuery("select room_id from room where is_booked = 0");
                availableRoomList = new ArrayList<>();
                while (res.next()) {
                    availableRoomList.add(res.getString("room_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (res == null) res.close();
                    if (statement == null) statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return availableRoomList;
            }
        } else return availableRoomList;
    }

    private boolean checkoutDatabase(String room_id) {
        boolean result = false;
        Connection connection = DataBaseConnect.getConnection();
        if (connection != null) {
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            try {
                connection.setAutoCommit(false);
                String sql1 = String.format(
                        "update room set is_booked = 0 where room_id = '%s'",
                        room_id
                );
                String sql2 = String.format(
                        "delete customer where room_id = '%s'",
                        room_id
                );
                ps1 = connection.prepareStatement(sql1);
                ps1.execute();
                ps2 = connection.prepareStatement(sql2);
                ps2.execute();
                connection.commit();
                result = true;
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    if (ps1 != null) ps1.close();
                    if (ps2 != null) ps2.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public synchronized void refreshSocket(Socket socket) {
        if (sockets.containsKey(socket)) {
            sockets.replace(socket, System.currentTimeMillis());
        }
    }
}
