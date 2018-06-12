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

    public void login(Customer customer, Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
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
        }
    }

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
