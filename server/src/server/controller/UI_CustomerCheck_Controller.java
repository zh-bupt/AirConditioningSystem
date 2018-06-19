package server.controller;

import server.manager.BillManager;
import server.manager.CustomerManager;
import server.simpleclass.Bill;
import server.simpleclass.Customer;
import server.ui.UI_CustomerCheck;
import server.ui.UI_Register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.*;
import java.util.Timer;

public class UI_CustomerCheck_Controller {
    private UI_CustomerCheck ui_customerCheck;
    private List<String> checkingRooms = new ArrayList<>();
    private String room_id;
    private Map<Socket, String> customerMap = null;
    private Bill bill;

    private void getData(){
        checkingRooms.clear();
        customerMap = CustomerManager.getInstance().getCustomerMap();
        if(customerMap!=null)
        {
            Iterator<Socket> iterator = customerMap.keySet().iterator();
            while (iterator.hasNext()) {
                Socket key = iterator.next();
                checkingRooms.add(customerMap.get(key));
            }
            Collections.sort(checkingRooms);
        }
        else
        {
            checkingRooms = new ArrayList<>();
            checkingRooms.add("Room1");
            checkingRooms.add("Room2");
            checkingRooms.add("Room3");
            checkingRooms.add("Room4");
        }


    }
    class Button1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            room_id = ui_customerCheck.getRoomID();
            bill = BillManager.getInstance().getBill(room_id);
            ui_customerCheck.setTextArea("房间号："+bill.getRoomId()+"\n电量："+bill.getElectricity()+"\n费用："+bill.getCost()+"\n创建时间:"+bill.getCreateTime());
            if(CustomerManager.getInstance().check(room_id))
            {
                JOptionPane.showMessageDialog(ui_customerCheck,"结账离开成功！账单信息请看下方文本框！"+"房间号："+room_id);
                getData();
                ui_customerCheck.setCheckingRooms(checkingRooms);

                ui_customerCheck.refreshUI();
            }
            else
                JOptionPane.showMessageDialog(ui_customerCheck,"结账离开失败！");
        }
    }
    public UI_CustomerCheck_Controller(UI_CustomerCheck customerCheck) {
        ui_customerCheck = customerCheck;
        getData();
//        bill = BillManager.getInstance().getBill("1001");
//        ui_customerCheck.setTextArea("房间号："+bill.getRoomId()+"\n电量："+bill.getElectricity()+"\n费用："+bill.getCost()+"\n创建时间:"+bill.getCreateTime());

        ui_customerCheck.setCheckingRooms(checkingRooms);
        ui_customerCheck.refreshUI();
        ui_customerCheck.addButton1Listener(new Button1Listener());

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
        UI_CustomerCheck_Controller ui_con = new UI_CustomerCheck_Controller(new UI_CustomerCheck());
    }
}

