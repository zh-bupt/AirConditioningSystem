import org.json.JSONObject;
import simpleclass.Customer;

import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private static CustomerManager customerManager = null;
    private Map<Socket, String> customerMap = new HashMap<>();


    private CustomerManager(){
        try {
            CycleTask cycleTask = new CycleTask(5, 2);
            new Thread(cycleTask).start();
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

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

    class CycleTask implements Runnable{
        private int queryInterval;
        private int billInterval;

        public CycleTask(int queryInterval, int billInterval) throws InvalidAttributeValueException {
            if (queryInterval <= 0 || queryInterval > 59)
                throw new InvalidAttributeValueException("Query interval should between 1 and 59");
            if (billInterval <= 0 || billInterval > 59)
                throw new InvalidAttributeValueException("Bill interval should between 1 and 59");
            this.queryInterval = queryInterval;
            this.billInterval = billInterval;
        }

        private int[][] getScheduleTable() {
            int table[][] = new int[2][60];
            for (int i = 0; i < 60; ++i) {
                if (i % queryInterval == 0) table[0][i] = 1;
                else table[0][i] = 0;
                if (i % billInterval == 0) table[1][i] = 1;
                else table[1][i] = 0;
            }
            return table;
        }

        private void broadCast(String query) {
            for (Socket socket : customerMap.keySet()) {
                TCPServer.getInstance().sendData(socket, query);
            }
        }

//        private void seedData(Socket socket, String data) {
//            try{
//                System.out.println("send to" + socket.toString());
//                PrintWriter writer=new PrintWriter(socket.getOutputStream());
//                writer.print(data);
//                writer.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        @Override
        public void run() {
            int[][] table = getScheduleTable();
            int index = 0;
            int querySeq = 0, billSeq = 0;
            while(true) {
                if (table[0][index] == 1) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "state_query");
                    map.put("seq", querySeq++);
                    String query = new JSONObject(map).toString();
                    broadCast(query);
                    System.out.println("Send state query info");
                }
                if (table[1][index] == 1) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "bill");
                    map.put("seq", billSeq++);
                    map.put("power", 123);
                    map.put("money", 12.22);
                    // TODO 添加广播用户账单信息
                    String bill = new JSONObject(map).toString();
                    broadCast(bill);
                    System.out.println("Send bill info");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index = (index + 1) % 60;
            }
        }
    }

}
