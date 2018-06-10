import simpleclass.Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet result = DataBaseConnect.
                query("select id from customer where room_id = " + "'" + roomId + "'");
        try {
            if (result != null && result.next() && id.equals(result.getString(1)))
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getRoomId(Socket socket) {
        return customerMap.get(socket);
    }

    public String removeCustomer(Socket socket) {
        return customerMap.remove(socket);
    }

}
