package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_Reporter extends JFrame {
    private JTextArea area;
    public void setTextArea(String i){
        area.setText(i);
    }

    public String getTextField_roomID() {
        return textField_roomID.getText();
    }

    private JTextField textField_roomID;
    private JLabel roomNoLabel;
    private JButton button1;

    public int getTime() {
        return time;
    }

    private int time;

    public UI_Reporter(int i){
        init(i);
        setVisible(true);
        setSize(300,300);
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

        area.setBounds(10,50, 280,210);
        this.add(roomNoLabel);
        this.add(area);
        this.add(textField_roomID);
        this.add(button1);
        this.setLayout(null);

    }
    public void addButton1Listener(ActionListener mal) {
        button1.addActionListener(mal);
    }


    public static void main(String[] args){
        UI_Reporter ui_comm = new UI_Reporter(0);
    }

}
