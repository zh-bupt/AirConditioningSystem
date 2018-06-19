package server.controller;

import server.Reporter;
import server.manager.CustomerManager;
import server.simpleclass.Customer;
import server.ui.UI_Register;
import server.ui.UI_Reporter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

public class UI_Reporter_Controller {
    private UI_Reporter ui_reporter;
    Reporter reporter;
    int time;
    String startTime;
    String endTime;

    class Button1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            reporter = new Reporter(ui_reporter.getTextField_roomID(),startTime,endTime);
            ui_reporter.setTextArea(reporter.getBillList()+"\n\n"+reporter.getRequestList()+"\n\n"+reporter.getStartTimes()+"\n\n"+reporter.getTotalCost());
        }
    }
    public UI_Reporter_Controller(UI_Reporter register) {
        ui_reporter = register;
        time = ui_reporter.getTime();
        if(time == 0)
        {
            Date startTime1 = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime1);
            calendar.add(Calendar.HOUR, -24);
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
            endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime1);
            System.out.println("Starttime:"+startTime+"endTime:"+endTime+"哈哈哈");
        }
        else if (time == 1){
            Date startTime1 = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime1);
            calendar.add(Calendar.DATE, -7);
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
            endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime1);
            System.out.println("Starttime:"+startTime+"endTime:"+endTime+"哈哈哈1");
        }else if (time == 2){
            Date startTime1 = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime1);
            calendar.add(Calendar.MONTH, -1);
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
            endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime1);
            System.out.println("Starttime:"+startTime+"endTime:"+endTime+"哈哈哈2");
        }

        ui_reporter.addButton1Listener(new Button1Listener());

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
        UI_Reporter_Controller ui_con = new UI_Reporter_Controller(new UI_Reporter(0));
    }
}

