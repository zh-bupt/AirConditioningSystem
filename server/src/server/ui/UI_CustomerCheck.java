package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class UI_CustomerCheck extends JFrame {
    private JTextArea textArea = new JTextArea();
    private JComboBox comboBox1;
    private JLabel l1,l2;
    private JButton button1;
    private List<String> checkingRooms=new ArrayList<>();
    public UI_CustomerCheck(){
        init();
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }



    private void init(){
        this.setTitle("结账离开");

        l1 = new JLabel("房间号:");
        l1.setBounds(50,10,80,30);
        l2 = new JLabel("账单:");
        l2.setBounds(50,40,80,30);
        checkingRooms.add("India");
        checkingRooms.add("Aus");
        checkingRooms.add("U.S.A");
        comboBox1=new JComboBox(checkingRooms.toArray());
        comboBox1.setBounds(110, 10,160,30);
        textArea.setBounds(50,70,200,150);



        this.add(l1);this.add(comboBox1);
        this.add(l2);
        this.add(textArea);
        button1 = new JButton("结账离开");
        button1.setBounds(100,230,80,30);
        this.add(button1);

        this.setLayout(null);

    }

    public void setCheckingRooms(List<String> rooms){
        checkingRooms = rooms;
    }

    public void refreshUI(){
        remove(comboBox1);
        comboBox1=new JComboBox(checkingRooms.toArray());
        comboBox1.setBounds(110, 10,160,30);
        this.add(comboBox1);

        revalidate();
        repaint();
    }

    public String getRoomID(){
        return comboBox1.getItemAt(comboBox1.getSelectedIndex()).toString();
    }

    public void setTextArea(String i){
        textArea.setText(i);
    }

    public void addButton1Listener(ActionListener mal) {
        button1.addActionListener(mal);
    }

    public static void main(String[] args){
        UI_CustomerCheck ui_comm = new UI_CustomerCheck();
    }

}
