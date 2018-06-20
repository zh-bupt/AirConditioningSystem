package server.ui;

import server.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.util.*;
import java.util.Timer;


public class UI extends JFrame {
    private static UI ui = null;
    private JMenuBar menuBar;
    private JMenu menuReporter;
    private JMenuItem itemDayReporter,itemWeekReporter,itemMonthReporter;
    private JMenu menuCustomerManager;
    private JMenuItem itemCustomerRegister,itemCustomerCheck;
    private JMenu menuSettings;
    private JMenuItem itemSettings,itemRatesSetting,itemModeSetting,itemAlgorithmSetting,itemRefreshSetting,itemRoomNoSetting;
    private JMenu menuView;
    private JMenuItem itemViewStatus,itemViewErrors;
    private JMenu menuAccount;
    private JMenuItem itemAddAccount,itemSetAccount;
    private JMenu menuPower;
    private JMenuItem itemStart,itemShutDown,itemRestart;
    private JPanel p;
    private GridLayout glayout;

//    private JButton powerButton = new JButton("打开电源");;
    private int power = 0;
    private int roomNo = 20;
    private Map<Socket, String> customerMap = new HashMap<>();

    public void setPower(int i){
        power = i;
    }

    public void setRoomNo(int i){
        roomNo=i;
    }

    public static UI getInstance() {
        if (ui == null) ui = new UI("中央空调管理系统");
        return ui;
    }

    private UI(){}
    private UI(String title){
        init(title);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }




    void init(String title){
        setTitle(title);
        menuBar = new JMenuBar();
//        menuPower = new JMenu("电源");
        menuCustomerManager = new JMenu("住客");
        menuReporter = new JMenu("报表");
        menuSettings=new JMenu("设置");
        menuView = new JMenu("查看");
//        menuAccount = new JMenu("账户");

//        itemStart = new JMenuItem("开机");
//        itemStart.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                power =1;
//                changePowerItem();
//            }
//        });
//        itemShutDown = new JMenuItem("关机");
//        itemShutDown.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                power =0;
//                changePowerItem();
//            }
//        });
//        itemRestart = new JMenuItem("重启");
//        itemRestart.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                power =1;
//                changePowerItem();
//            }
//        });

//        menuPower.add(itemStart);
//        menuPower.add(itemShutDown);
//        menuPower.add(itemRestart);

        itemCustomerRegister = new JMenuItem("入住登记");
        itemCustomerRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UI_Register_Controller(new UI_Register());
            }
        });
        itemCustomerCheck = new JMenuItem("结账离开");
        itemCustomerCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UI_CustomerCheck_Controller(new UI_CustomerCheck());
            }
        });

        menuCustomerManager.add(itemCustomerRegister);
        menuCustomerManager.add(itemCustomerCheck);

        itemDayReporter = new JMenuItem("当日报表");
        itemDayReporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UI_Reporter_Controller(new UI_Reporter(0));
            }
        });
        itemWeekReporter = new JMenuItem("当周报表");
        itemWeekReporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UI_Reporter_Controller(new UI_Reporter(1));
            }
        });
        itemMonthReporter = new JMenuItem("当月报表");
        itemMonthReporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UI_Reporter_Controller(new UI_Reporter(2));
            }
        });
        menuReporter.add(itemDayReporter);
        menuReporter.add(itemWeekReporter);
        menuReporter.add(itemMonthReporter);

        itemSettings = new JMenuItem("配置空调");
        itemSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UI_Settings_Controller(new UI_Settings());
            }
        });
        menuSettings.add(itemSettings);
//        itemRatesSetting = new JMenuItem("费率设置");
//        itemRatesSetting.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_RatesSetting();
//            }
//        });
//        itemModeSetting = new JMenuItem("模式设置");
//        itemModeSetting.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_ModeSetting();
//            }
//        });
//        itemAlgorithmSetting = new JMenuItem("调度算法设置");
//        itemAlgorithmSetting.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_AlgorithmSetting();
//            }
//        });
//        itemRefreshSetting = new JMenuItem("刷新频率设置");
//        itemRefreshSetting.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_RefreshSetting();
//            }
//        });
//        itemRoomNoSetting = new JMenuItem("房间数量设置");
//        itemRoomNoSetting.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_RoomNoSetting();
//            }
//        });
//        menuSettings.add(itemRatesSetting);
//        menuSettings.add(itemModeSetting);
//        menuSettings.add(itemAlgorithmSetting);
//        menuSettings.add(itemRefreshSetting);
//        menuSettings.add(itemRoomNoSetting);

//        itemViewStatus = new JMenuItem("查看从机状态");
//        itemViewStatus.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_ViewStatus();
//            }
//        });
//        itemViewErrors = new JMenuItem("查看从机故障");
//        itemViewErrors.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_ViewErrors();
//            }
//        });
//        menuView.add(itemViewStatus);
//        menuView.add(itemViewErrors);

//        itemAddAccount = new JMenuItem("添加账户");
//        itemAddAccount.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_ViewErrors();
//            }
//        });
//        itemSetAccount = new JMenuItem("管理账户");
//        itemSetAccount.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UI_ViewErrors();
//            }
//        });
//        menuAccount.add(itemAddAccount);
//        menuAccount.add(itemSetAccount);


//        menuBar.add(menuPower);
        menuBar.add(menuCustomerManager);
        menuBar.add(menuReporter);
        menuBar.add(menuSettings);
//        menuBar.add(menuView);
//        menuBar.add(menuAccount);


        setJMenuBar(menuBar);
        refreshUI();
    }

//    public void addPowerButtonListener(ActionListener mal) {
//        powerButton.addActionListener(mal);
//    }
//    public String getPowerButtonText(){
//        return powerButton.getText();
//    }

    private void refreshUI(){

        glayout = new GridLayout(0,4,5,3);
        p = new JPanel(glayout);
//        if(this.power==0){
//            powerButton.setText("打开电源");
//            powerButton.setBackground(Color.GREEN);
//        }else{
//            powerButton.setText("关闭电源");
//            powerButton.setBackground(Color.RED);
//        }
//        powerButton.setOpaque(true);
//        powerButton.setBorderPainted(false);
//        p.add(powerButton);
        p.add(new JLabel(""));
        p.add(new JLabel(""));
        p.add(new JLabel(""));

        JButton t[]= new JButton[roomNo];
        int i=0;
        Iterator<Socket> iterator = customerMap.keySet().iterator();
        while (iterator.hasNext()) {
            Socket key = iterator.next();
            System.out.println(key + "　：" + customerMap.get(key));
            t[i]=new JButton("房间"+customerMap.get(key));
            t[i].setActionCommand(customerMap.get(key));
            t[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String choice = (String) e.getActionCommand();
                    new UI_RoomStatus_Controller(new UI_RoomStatus(choice));
                }
            });
            p.add(t[i]);
            i++;
        }
//        changePowerItem();
        p.add(new JLabel("欢迎来到中央空调管理系统！"));
        getContentPane().add(p,BorderLayout.CENTER);

    }
    public void setRoomButton(Map<Socket, String> customerMap1){
        customerMap = customerMap1;
    }
    public void updateUI(){
        ui.p.removeAll();
        refreshUI();
        ui.p.revalidate();
        ui.p.repaint();
    }

//    private void changePowerItem(){
//        if(power==0){
//            itemStart.setEnabled(true);
//            itemShutDown.setEnabled(false);
//            itemRestart.setEnabled(true);
//            powerButton.setText("打开电源");
//            powerButton.setBackground(Color.green);
//        }else if(power==1){
//            itemStart.setEnabled(false);
//            itemShutDown.setEnabled(true);
//            itemRestart.setEnabled(true);
//            powerButton.setText("关闭电源");
//            powerButton.setBackground(Color.red);
//        }
//    }

    public static void main(String[] args){
        UI ui = UI.getInstance();
        ui.setBounds(10,10,600,450);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ui.p.removeAll();
                ui.refreshUI();
                ui.p.revalidate();
                ui.p.repaint();

                System.out.println("Inside Timer Task" + System.currentTimeMillis());
            }
        };

        System.out.println("Current time" + System.currentTimeMillis());
        timer.schedule(task, 1000,1000);
        System.out.println("Current time" + System.currentTimeMillis());

    }
}
