package server.controller;

import server.manager.CustomerManager;
import server.simpleclass.Customer;
import server.ui.UI_Register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UI_Register_Controller {
    private UI_Register ui_register;
    List<String> availableRooms;
    Customer customer;

    class Button1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            customer = new Customer(ui_register.getRoomID(),ui_register.getUserID());
            if(CustomerManager.getInstance().Register(customer))
            {
                JOptionPane.showMessageDialog(ui_register,"登记入住成功！"+"房间号："+ui_register.getRoomID()+"身份证号:"+ui_register.getUserID());
                ui_register.refreshUI();
            }
            else
                JOptionPane.showMessageDialog(ui_register,"登记入住失败！");
        }
    }
    public UI_Register_Controller(UI_Register register) {
        ui_register = register;
        if(CustomerManager.getInstance().getAvailableRoom()!=null)
            availableRooms = CustomerManager.getInstance().getAvailableRoom();
        else
        {
            availableRooms = new ArrayList<>();
            availableRooms.add("Room1");
            availableRooms.add("Room2");
            availableRooms.add("Room3");
            availableRooms.add("Room4");
        }
        System.out.println(availableRooms);
        ui_register.setAvailableRooms(availableRooms);
        ui_register.refreshUI();
        ui_register.addButton1Listener(new Button1Listener());

//        final int[] i = {1};
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                if(100-i[0]>20)
//                {
//                    i[0]++;
//                }
//                ui_register.refreshUI();
//                System.out.println("Inside Timer Task" + System.currentTimeMillis());
//            }
//        };
//        timer.schedule(task, 1000,1000);

    }


    public static void main(String[] args){
        UI_Register_Controller ui_con = new UI_Register_Controller(new UI_Register());
    }
}

