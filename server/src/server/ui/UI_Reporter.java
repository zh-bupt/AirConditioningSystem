package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_Reporter extends JFrame {
    private JTextArea area;
    private JTable table_bill;
    private JTable table_request;
    private JLabel label_bill,label_request,label_startTimes,label_allFees;
    public void setTextArea(String i){
        area.setText(i);
    }

    public String getTextField_roomID() {
        return textField_roomID.getText();
    }

    private JTextField textField_roomID;
    private JLabel roomNoLabel;
    private JButton button1,button2;

    public int getTime() {
        return time;
    }

    private int time;

    public UI_Reporter(int i){
        init(i);
        setVisible(true);
        setSize(600,650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(int i){
        if(i==0){
            setTitle("当日报表");
            time = 0;
            area=new JTextArea("当日报表");
        }else if(i==1){
            setTitle("当周报表");
            time =1;
            area=new JTextArea("当周报表");
        }else if(i==2){
            setTitle("当月报表");
            time =2;
            area=new JTextArea("当月报表");
        }

        roomNoLabel = new JLabel("房间号:");
        roomNoLabel.setBounds(10,10,50,20);
        textField_roomID = new JTextField("1001");
        textField_roomID.setBounds(70,10,50,20);
        button1 = new JButton("查询");
        button1.setBounds(130,10,50,20);
        button2 = new JButton("导出");
        button2.setBounds(200,10,50,20);

//        area.setBounds(10,50, 280,210);

//        JScrollPane scrollableTextArea = new JScrollPane(area);
//
//        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollableTextArea.setBounds(10,50,280,210);
//        this.add(scrollableTextArea);
        label_bill = new JLabel("所有费用：");
        label_bill.setBounds(10,50,100,30);
        label_request = new JLabel("所有请求：");
        label_request.setBounds(10,290,100,30);
        label_startTimes = new JLabel("开关次数：");
        label_startTimes.setBounds(10,530,200,30);
        label_allFees = new JLabel("所有费用：");
        label_allFees.setBounds(10,560,200,30);
        this.add(label_startTimes);
        this.add(label_allFees);
        this.add(roomNoLabel);
//        this.add(area);
        this.add(textField_roomID);
        this.add(button1);
        this.add(button2);
        this.setLayout(null);

    }
    public void addButton1Listener(ActionListener mal) {
        button1.addActionListener(mal);
    }
    public void addButton2Listener(ActionListener mal) {
        button2.addActionListener(mal);
    }
    public void setTable_bill(String data[][],String column[]){
        table_bill = new JTable(data,column);
        table_bill.setCellSelectionEnabled(true);
        JScrollPane scrollableTextArea = new JScrollPane(table_bill);

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setBounds(10,80,580,210);
        this.add(scrollableTextArea);
    }

    public void setTable_request(String data[][],String column[]){
        table_request = new JTable(data,column);
        table_request.setCellSelectionEnabled(true);
        JScrollPane scrollableTextArea = new JScrollPane(table_request);

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setBounds(10,320,580,210);
        this.add(scrollableTextArea);
    }

    public void setLabel_startTimes(String i){
        label_startTimes.setText(i);
    }

    public void setLabel_allFees(String i){
        label_allFees.setText(i);
    }



    public static void main(String[] args){
        UI_Reporter ui_comm = new UI_Reporter(0);
    }

}
