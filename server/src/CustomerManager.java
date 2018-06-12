import simpleclass.Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private static CustomerManager customerManager = null;
    private Map<Socket, String> customerMap = new HashMap<>();


    private CustomerManager(){}

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
                        customerMap.put(socket, customer.getRoom_id());
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
        if (result != null && id.equals(result.get(0).get("id")))
                return true;
        return false;
    }

    public String getRoomId(Socket socket) {
        return customerMap.get(socket);
    }

    public String removeCustomer(Socket socket) {
        String room_id = customerMap.remove(socket);
        StateManager.getInstance().removeRoom(room_id);
        return room_id;
    }

}
