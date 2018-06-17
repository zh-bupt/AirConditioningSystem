package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_RefreshSetting extends JFrame implements ActionListener{
    private JTextField textField1;
    private JLabel l1;
    private JButton button1;
    public UI_RefreshSetting(){
        init();
        setVisible(true);
        setSize(300,200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(){
        this.setTitle("刷新频率设置");

        l1 = new JLabel("刷新频率(ms):");
        l1.setBounds(50,50,100,30);
        textField1 = new JTextField("1000");
        textField1.setBounds(150,50,100,30);
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
        UI_RefreshSetting ui_comm = new UI_RefreshSetting();
    }

}
