package server;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.mapper.CustomerMapper;
import server.mapper.RequestMapper;
import server.simpleclass.Customer;
import server.simpleclass.Request;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        TCPServer server = TCPServer.getInstance();
        server.init();
        Thread t = new Thread(server);
        t.start();
//        System.out.println("开关机次数:" +
//                Reporter.getStartTimes(
//                        "6616",
//                        "2018-06-14 00:00:00",
//                        "2018-06-14 23:59:59"
//                )
//        );
//
//        new RequestMapper().insert(new Request("6616", "2018-06-14 17:01:00", 23, "low"));
//        List<Request> list = Reporter.getRequestList(
//                "6616",
//                "2018-06-11 00:00:00",
//                "2018-06-11 23:59:59"
//        );
//        for (Request r : list) {
//            System.out.println(r.getRoomId() + " " + r.getStartTime());
//        }
//        Customer customer = (Customer) new CustomerMapper().get("select * from customer where room_id = '6616'");
//        System.out.println(customer.toString());
//        try {
//            new CustomerMapper().insert(new Customer("6617", "12345678"));
//        } catch (SQLServerException e) {
//            e.printStackTrace();
//        }
//        List<Customer> list = new CustomerMapper().gets("select * from customer");
//        System.out.println(list);
//        float total_cost = Reporter.getTotalCost("6616", "2018-06-14 00:00:00", "2018-06-14 23:59:59");
//        System.out.println("Total cost:" + total_cost);
    }
}
