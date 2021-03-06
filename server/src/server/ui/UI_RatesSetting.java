package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_RatesSetting extends JFrame implements ActionListener{
    private JTextField textField1;
    private JLabel l1;
    private JButton button1;
    public UI_RatesSetting(){
        init();
        setVisible(true);
        setSize(300,200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(){
        this.setTitle("费率设置");

        l1 = new JLabel("费率(元):");
        l1.setBounds(50,50,80,30);
        textField1 = new JTextField("5");
        textField1.setBounds(130,50,100,30);
        this.add(l1);this.add(textField1);
        button1 = new JButton("设置");
        button1.setBounds(100,100,80,30);
        button1.addActionListener(this);
        this.add(button1);

        this.setLayout(null);

    }

    public void actionPerformed(ActionEvent e){
        JOptionPane.showMessageDialog(this,"设置成功！");
    }

    public static void main(String[] args){
        UI_RatesSetting ui_comm = new UI_RatesSetting();
    }

}
