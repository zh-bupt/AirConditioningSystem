package server.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_Settings extends JFrame {
    private JLabel label_mode;
    private JRadioButton r1,r2;
    private ButtonGroup bg;

    private JLabel label_bill;
    private JTextField textField_bill;


    private JLabel label_status;
    private JTextField textField_status;

    private JLabel label_price;
    private JTextField textField_price;

    private JLabel label_power,label_power_low,label_power_medium,label_power_high;

    public String getModeSelected(){
        if(r1.isSelected()){
            return "winter";
        }else if(r2.isSelected()){
            return "summer";
        }
        return null;
    }
    public String getTextField_bill() {
        return textField_bill.getText();
    }

    public String getTextField_status() {
        return textField_status.getText();
    }

    public String getTextField_price() {
        return textField_price.getText();
    }

    public String getTextField_power_low() {
        return textField_power_low.getText();
    }

    public String getTextField_power_medium() {
        return textField_power_medium.getText();
    }

    public String getTextField_power_high() {
        return textField_power_high.getText();
    }

    private JTextField textField_power_low,textField_power_medium,textField_power_high;

    private JButton button1;
    public UI_Settings(){
        init();
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(){
        this.setTitle("设置");
        label_mode = new JLabel("工作模式:");
        r1=new JRadioButton("A) 制热",true);
        r2=new JRadioButton("B) 制冷");
        label_mode.setBounds(10,10,80,30);
        r1.setBounds(90,10,80,30);
        r2.setBounds(170,10,80,30);
        bg=new ButtonGroup();
        bg.add(r1);bg.add(r2);
        this.add(label_mode);
        this.add(r1);this.add(r2);

        label_bill = new JLabel("账单发送频率(秒/次)：");
        textField_bill = new JTextField("1");
        label_bill.setBounds(10,40,150,30);
        textField_bill.setBounds(160,40,70,30);
        this.add(label_bill);this.add(textField_bill);

        label_status = new JLabel("状态更新频率(秒/次)：");
        textField_status = new JTextField("1");
        label_status.setBounds(10,70,150,30);
        textField_status.setBounds(160,70,70,30);
        this.add(label_status);this.add(textField_status);

        label_price = new JLabel("电费价格(元/千瓦时)：");
        textField_price = new JTextField("0.5");
        label_price.setBounds(10,100,150,30);
        textField_price.setBounds(160,100,70,30);
        this.add(label_price);this.add(textField_price);

        label_power = new JLabel("能耗设置：");
        label_power.setBounds(10,130,150,30);
        this.add(label_power);

        label_power_low = new JLabel("低风速(千瓦时/分)：");
        textField_power_low = new JTextField("0.5");
        label_power_low.setBounds(30,160,150,30);
        textField_power_low.setBounds(180,160,70,25);
        this.add(label_power_low);this.add(textField_power_low);

        label_power_medium = new JLabel("中风速(千瓦时/分)：");
        textField_power_medium = new JTextField("1");
        label_power_medium.setBounds(30,180,150,30);
        textField_power_medium.setBounds(180,180,70,25);
        this.add(label_power_medium);this.add(textField_power_medium);

        label_power_high = new JLabel("高风速(千瓦时/分)：");
        textField_power_high = new JTextField("1.5");
        label_power_high.setBounds(30,200,150,30);
        textField_power_high.setBounds(180,200,70,25);
        this.add(label_power_high);this.add(textField_power_high);


        button1 = new JButton("设置");
        button1.setBounds(100,240,80,30);

        this.add(button1);

        this.setLayout(null);

    }

    public void refreshUI(String mode,float price,float low,float medium,float high,int query_interval,int bill_send_interval){
        r1.setSelected(mode=="summer");
        r2.setSelected(mode=="winter");
        textField_price.setText(String.valueOf(price));
        textField_power_low.setText(String.valueOf(low));
        textField_power_medium.setText(String.valueOf(medium));
        textField_power_high.setText(String.valueOf(high));
        textField_status.setText(String.valueOf(query_interval));
        textField_bill.setText(String.valueOf(bill_send_interval));
    }

//    public void actionPerformed(ActionEvent e){
//        JOptionPane.showMessageDialog(this,"设置成功！");
//    }

    public void addButton1Listener(ActionListener mal) {
        button1.addActionListener(mal);
    }

    public static void main(String[] args){
        UI_Settings ui_comm = new UI_Settings();
    }

}
