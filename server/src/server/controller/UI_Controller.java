package server.controller;

import server.manager.CustomerManager;
import server.ui.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.*;

public class UI_Controller {
    private UI ui;
    private Map<Socket, String> customerMap = null;

    public UI_Controller() {
        ui = UI.getInstance();
        ui.setBounds(10,10,600,450);
        ui.addPowerButtonListener(new PowerButtonListener());
        if(!CustomerManager.getInstance().getCustomerMap().isEmpty())
        {
            customerMap = CustomerManager.getInstance().getCustomerMap();
        }
        else
        {
            customerMap = new HashMap<>();
            for(int i=0;i<30;i++)
            {
                customerMap.put(new Socket(), Integer.toString(i));
            }
            System.out.println("error");
        }
        ui.setRoomButton(customerMap);

        System.out.println(customerMap.size());
        ui.setRoomNo(customerMap.size());

        final int[] i = {1};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                i[0]++;

//                if(customerMap.size()>=15)
//                {
//                    for (Map.Entry<Socket, String> item : customerMap.entrySet()){
//                        customerMap.remove(item.getKey());
//                        break;
//                    }
//                }
                customerMap = CustomerManager.getInstance().getCustomerMap();
                ui.setRoomNo(customerMap.size());
                ui.setRoomButton(customerMap);
                ui.updateUI();
                System.out.println("Inside Timer Task" + System.currentTimeMillis());
            }
        };
        System.out.println("Current time" + System.currentTimeMillis());
        timer.schedule(task, 1000,1000);
        System.out.println("Current time" + System.currentTimeMillis());
    }

    class PowerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (ui.getPowerButtonText()=="打开电源"){
                ui.setPower(1);
                ui.updateUI();
            }
            else if(ui.getPowerButtonText()=="关闭电源")
            {
                ui.setPower(0);
                ui.updateUI();
            }
        }
    }

    public static void main(String[] args){
        UI_Controller ui_con = new UI_Controller();
    }
}

