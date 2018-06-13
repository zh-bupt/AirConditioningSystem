import org.json.JSONObject;
import simpleclass.Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class CustomerManager implements Observer {
    private static CustomerManager customerManager = null;

    // 当前连接在服务器上的用户的map, key为socket, value为room_id
    private Map<Socket, String> customerMap = new HashMap<>();
    private int querySeq = 0;
    private int billSeq = 0;


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
                        // TODO 改成观察者模式, 这里仅测试用
                        BillManager.getInstance().addBill(customer.getRoom_id());
                        ack = "{\"type\":\"login_ack\",\"result\":\"succeed\"}";
                        System.out.println("Welcome customer " + customer.getId() + " in room " + customer.getRoom_id());
                        System.out.println("Login succeeded!");
                    } else {
                        ack = "{\"type\":\"login_ack\",\"result\":\"failed\"}";
                        System.out.println("Login failed!");
                    }
                    ack = StringUtils.getHead(ack.length()) + ack;
                    printWriter.print(ack);
                    printWriter.flush();
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
        String type = (String)arg;

        //处理 TimerSubject 的消息, 有query消息和bill消息
        if ("query".equals(type) && customerMap.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("type", "state_query");
            map.put("seq", querySeq++);
            String query = new JSONObject(map).toString();
            broadCast(query);
            System.out.println("Send state query info");
        }
        if ("send_bill".equals(type) && customerMap.size() > 0) {
            HashMap<String, Float> billMap = BillManager.getInstance().getBillMap();
            for (Socket s : customerMap.keySet()) {
                float power = billMap.get(customerMap.get(s)).floatValue();
                Map<String, Object> map = new HashMap<>();
                map.put("type", "bill");
                map.put("seq", billSeq);
                map.put("power", power);
                map.put("money", power * 5);
                String bill = new JSONObject(map).toString();
                TCPServer.getInstance().sendData(s, bill);
            }
            billSeq++;
            System.out.println("Send bill info");
        }
    }

    /*
     * @Description getCustomerMap 返回用户的map, key为socket, value为room_id
     * @Param
     * @Return Map<Socket, String>
     */
    public Map<Socket, String> getCustomerMap() {
        return customerMap;
    }
}
