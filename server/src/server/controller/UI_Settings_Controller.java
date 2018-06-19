package server.controller;

import org.json.JSONObject;
import server.TCPServer;
import server.manager.CustomerManager;
import server.simpleclass.Customer;
import server.ui.UI_Register;
import server.ui.UI_Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UI_Settings_Controller {
    private UI_Settings ui_settings;
    private String mode;
    private float price;
    private float low;
    private float medium;
    private float high;
    private int query_interval,bill_send_interval;

    TCPServer tcpServer;

    class Button1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            try
            {
                mode = ui_settings.getModeSelected();
                price = Float.valueOf(ui_settings.getTextField_price());
                low = Float.valueOf(ui_settings.getTextField_power_low());
                medium = Float.valueOf(ui_settings.getTextField_power_medium());
                high = Float.valueOf(ui_settings.getTextField_power_high());
                query_interval = Integer.valueOf(ui_settings.getTextField_status());
                bill_send_interval = Integer.valueOf(ui_settings.getTextField_bill());

                JSONObject obj = new JSONObject();
                obj.put("mode",mode);
                obj.put("price",price);
                obj.put("low",low);
                obj.put("medium",medium);
                obj.put("high",high);
                obj.put("query_interval",query_interval);
                obj.put("bill_send_interval",bill_send_interval);
                obj.put("bill_update_interval",Float.valueOf(5));
                if(tcpServer.changeConfig(obj)){
                    JOptionPane.showMessageDialog(ui_settings,"设置成功！");
                }else{
                    JOptionPane.showMessageDialog(ui_settings,"设置失败，请检查是否输入有误！！");
                }

            }catch (Exception ee)
            {
                JOptionPane.showMessageDialog(ui_settings,"请检查是否输入有误！！");
            }
        }
    }
    public UI_Settings_Controller(UI_Settings settings) {
        ui_settings = settings;
        tcpServer = TCPServer.getInstance();
        mode = tcpServer.getMode();
        price = tcpServer.getPrice();
        low = tcpServer.getLow();
        medium = tcpServer.getMedium();
        high = tcpServer.getHigh();
        query_interval = 5;
        bill_send_interval = 2;


        ui_settings.refreshUI(mode, price, low, medium, high, query_interval, bill_send_interval);
        ui_settings.addButton1Listener(new Button1Listener());

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
        UI_Settings_Controller ui_con = new UI_Settings_Controller(new UI_Settings());
    }
}

