package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_ModeSetting extends JFrame implements ActionListener{
    private JRadioButton r1,r2;
    private ButtonGroup bg;
    private JButton button1;
    public UI_ModeSetting(){
        init();
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(){
        this.setTitle("模式设置");

        r1=new JRadioButton("A) 制热",true);
        r2=new JRadioButton("B) 制冷");
        r1.setBounds(100,50,200,30);
        r2.setBounds(100,100,200,30);
        bg=new ButtonGroup();
        bg.add(r1);bg.add(r2);
        this.add(r1);this.add(r2);

        button1 = new JButton("设置");
        button1.setBounds(100,150,80,30);
        button1.addActionListener(this);
        this.add(button1);

        this.setLayout(null);

    }

    public void actionPerformed(ActionEvent e){
        JOptionPane.showMessageDialog(this,"设置成功！");
    }

    public static void main(String[] args){
        UI_ModeSetting ui_comm = new UI_ModeSetting();
    }

}
