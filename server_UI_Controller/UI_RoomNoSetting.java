import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_RoomNoSetting extends JFrame implements ActionListener{
    private JTextField textField1;
    private JLabel l1;
    private JButton button1;
    public UI_RoomNoSetting(){
        init();
        setVisible(true);
        setSize(300,200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(){
        this.setTitle("房间数量设置");

        l1 = new JLabel("房间数量:");
        l1.setBounds(50,50,80,30);
        textField1 = new JTextField("20");
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
        UI.getInstance().setRoomNo(Integer.parseInt(textField1.getText()));

    }

    public static void main(String[] args){
        UI_RoomNoSetting ui_comm = new UI_RoomNoSetting();
    }

}
