package server.controller;

import server.Reporter;
import server.manager.CustomerManager;
import server.simpleclass.Bill;
import server.simpleclass.Customer;
import server.simpleclass.Request;
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
    private Reporter reporter;
    private int time;
    private String startTime;
    private String endTime;
    private List<Bill> billList;
    private List<Request> requestList;

    class Button1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            try{
                reporter = new Reporter(ui_reporter.getTextField_roomID(),startTime,endTime);
                billList = reporter.getBillList();
                requestList = reporter.getRequestList();

                ui_reporter.setTextArea(getBillData()+
                        "\n\n"+getRequestData()+
                        "\n\n开关次数:"+reporter.getStartTimes()+
                        "\n\n总费用:"+reporter.getTotalCost());
            } catch (Exception ee){
                JOptionPane.showMessageDialog(ui_reporter,"请检查输入错误！");
            }
        }
    }

    private String getBillData(){
        String billData="所有账单：";
        billData = billData+"\n创建时间"+"\t用电量"+"\t费用";
        ListIterator<Bill> itr=billList.listIterator();
        while(itr.hasNext()){
            Bill bill1 = itr.next();
            billData = billData+"\n"+bill1.getCreateTime()+"\t"+bill1.getElectricity()+"\t"+bill1.getCost();
        }
        return billData;
    }

    private String getRequestData(){
        String requestData="所有请求：";
        requestData = requestData+"\n开始时间"+"\t结束时间"+"\t开始温度"+"\t结束温度"+"\t目标温度"+"\t风速"+"\t用电量"+"\t电费";
        ListIterator<Request> itr=requestList.listIterator();
        while(itr.hasNext()){
            Request request1 = itr.next();
            requestData = requestData+"\n"+request1.getStartTime()+"\t"+request1.getStopTime()+"\t"+request1.getStartTemp()
                    +"\t"+request1.getStopTime()+"\t"+request1.getTargetTemp()+"\t"+request1.getWindPower()+"\t"+request1.getElectricity()
                    +"\t"+request1.getCost();
        }
        return requestData;
    }


    class Button2Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            try{
                reporter = new Reporter(ui_reporter.getTextField_roomID(),startTime,endTime);
                if(reporter.exportReport()){
                    JOptionPane.showMessageDialog(ui_reporter,"导出成功！请关注doc目录里生成的文件！");
                }
                else {
                    JOptionPane.showMessageDialog(ui_reporter,"导出失败！请查看是否有误！");
                }
            } catch (Exception ee){
                JOptionPane.showMessageDialog(ui_reporter,"请检查输入错误！");
            }
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
        ui_reporter.addButton2Listener(new Button2Listener());

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

