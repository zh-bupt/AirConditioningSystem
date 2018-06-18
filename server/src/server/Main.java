package server;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.manager.CustomerManager;
import server.mapper.BillMapper;
import server.mapper.RequestMapper;
import server.simpleclass.Bill;
import server.simpleclass.Customer;
import server.simpleclass.Request;
import server.ui.UI;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        UI ui = UI.getInstance();
        TCPServer server = TCPServer.getInstance();
        server.init();
        Thread t = new Thread(server);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println(CustomerManager.getInstance().getAvailableRoom().toString());
//        CustomerManager.getInstance().Register(new Customer("6616", "422801199611061437"));
//        System.out.println("开关机次数:" +
//                Reporter.getStartTimes(
//                        "6616",
//                        "2018-06-14 00:00:00",
//                        "2018-06-14 23:59:59"
//                )
//        );
//
//        try {
//            new RequestMapper().insert(new Request("6616", "2018-06-17 11:01:00", 23, "low"));
//        } catch (SQLServerException e) {
//            e.printStackTrace();
//        }
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

//        BillMapper mapper = new BillMapper();
//        Bill bill = new Bill("6616");
//        bill.setCost((float) 23.3);
//        bill.setElectricity(12);
//        try {
//            mapper.insert(bill);
//            List<Bill> list = mapper.gets("room_id = '6616'");
//            if (list != null && list.size() > 0) {
//                for (Bill b : list) System.out.println(b.toString());
//            }
//            System.out.println(list);
//        } catch (SQLServerException e) {
//            e.printStackTrace();
//        }
//        List<Request> list = null;
//        try {
//            list = new RequestMapper().gets("room_id = '6616' order by start_time");
//        } catch (SQLServerException e) {
//            e.printStackTrace();
//        }
//        if (list != null && list.size() > 0) {
//            for (Request r : list) {
//                System.out.println(r.getRoomId() + ":" + r.getStartTime());
//            }
//        }
//        new Reporter("6616", "2018-01-01 00:00:00", "2018-12-31 23:59:59").exportReport();
    }
}
