package server;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.manager.CustomerManager;
import server.mapper.BillMapper;
import server.mapper.RequestMapper;
import server.simpleclass.Bill;
import server.simpleclass.Customer;
import server.simpleclass.Request;
import server.controller.UI_Controller;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        UI_Controller ui_con = new UI_Controller();
//        TCPServer server = TCPServer.getInstance();
//        server.init();
//        Thread t = new Thread(server);
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
