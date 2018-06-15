import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_Bill extends JFrame {
    private JTextArea area;

    public UI_Bill(int i){
        init(i);
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(int i){
        if(i==0){
            setTitle("当日账单");
            area=new JTextArea("当日账单");
        }else if(i==1){
            setTitle("当周账单");
            area=new JTextArea("当周账单");
        }else if(i==2){
            setTitle("当月账单");
            area=new JTextArea("当月账单");
        }

        JLabel roomNoLabel = new JLabel("房间号:");
        roomNoLabel.setBounds(10,10,50,20);
        JTextField textField1 = new JTextField("1");
        textField1.setBounds(70,10,50,20);
        JButton button1 = new JButton("查询");
        button1.setBounds(130,10,50,20);

        area.setBounds(10,50, 280,210);
        this.add(roomNoLabel);
        this.add(area);
        this.add(textField1);
        this.add(button1);
        this.setLayout(null);

    }

    public static void main(String[] args){
        UI_Bill ui_comm = new UI_Bill(0);
    }

}
