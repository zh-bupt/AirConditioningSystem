package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_AlgorithmSetting extends JFrame implements ActionListener{
    private JRadioButton r1,r2,r3;
    private ButtonGroup bg;
    private JButton button1;
    public UI_AlgorithmSetting(){
        init();
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(){
        this.setTitle("调度算法设置");

        r1=new JRadioButton("A) 先来先到",true);
        r2=new JRadioButton("B) 高速风优先抢占");
        r3=new JRadioButton("C) 时间片轮询");
        r1.setBounds(75,50,200,30);
        r2.setBounds(75,100,200,30);
        r3.setBounds(75,150,200,30);
        bg=new ButtonGroup();
        bg.add(r1);bg.add(r2);bg.add(r3);
        this.add(r1);this.add(r2);this.add(r3);

        button1 = new JButton("设置");
        button1.setBounds(75,200,80,30);
        button1.addActionListener(this);
        this.add(button1);

        this.setLayout(null);

    }

    public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(this,"设置成功！");
    }

    public static void main(String[] args){
        UI_AlgorithmSetting ui_comm = new UI_AlgorithmSetting();
    }

}
