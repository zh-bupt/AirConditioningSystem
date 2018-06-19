package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class UI_CustomerCheck extends JFrame {
    private JTextField textField2;
    private JComboBox comboBox1;
    private JLabel l1,l2;
    private JButton button1;
    private List<String> checkingRooms=new ArrayList<>();
    public UI_CustomerCheck(){
        init();
        setVisible(true);
        setSize(300,200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }



    private void init(){
        this.setTitle("结账离开");

        l1 = new JLabel("房间号:");
        l1.setBounds(50,30,80,30);
        checkingRooms.add("India");
        checkingRooms.add("Aus");
        checkingRooms.add("U.S.A");
        comboBox1=new JComboBox(checkingRooms.toArray());
        comboBox1.setBounds(110, 30,160,30);


//        l2 = new JLabel("身份证号:");
//        l2.setBounds(50,80,80,30);
//        textField2 = new JTextField("5");
//        textField2.setBounds(110,80,160,30);


        this.add(l1);this.add(comboBox1);
//        this.add(l2);this.add(textField2);
        button1 = new JButton("结账离开");
        button1.setBounds(100,130,80,30);
        this.add(button1);

        this.setLayout(null);

    }

    public void setCheckingRooms(List<String> rooms){
        checkingRooms = rooms;
    }

    public void refreshUI(){
        remove(comboBox1);
        comboBox1=new JComboBox(checkingRooms.toArray());
        comboBox1.setBounds(110, 30,160,30);
        this.add(comboBox1);

        revalidate();
        repaint();
    }

    public String getRoomID(){
        return comboBox1.getItemAt(comboBox1.getSelectedIndex()).toString();
    }

//    public String getUserID(){
//        return textField2.getText();
//    }
    public void addButton1Listener(ActionListener mal) {
        button1.addActionListener(mal);
    }

    public static void main(String[] args){
        UI_CustomerCheck ui_comm = new UI_CustomerCheck();
    }

}
