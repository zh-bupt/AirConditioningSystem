import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_RoomStatus extends JFrame {
    private String roomNo;
    private JTextArea textArea = new JTextArea();
    private JButton button1;

    public UI_RoomStatus(String i){
        init(i);
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setTextArea(String i){
        textArea.setText(i);
    }
    public String getRoomNo(){
        return roomNo;
    }
    private void init(String i){
        roomNo = i;
//        JLabel roomNoLabel = new JLabel("房间号: "+i+" 号");
//        roomNoLabel.setBounds(100,60,150,30);
//        JLabel temperatureLabel = new JLabel("温度: "+i+" 摄氏度");
//        temperatureLabel.setBounds(100,90,150,30);
//        JLabel statusLabel = new JLabel("状态："+"正常运行");
//        statusLabel.setBounds(100,120,150,30);
        button1 = new JButton("立即刷新");
        button1.setBounds(100,210,80,30);

        textArea.setBounds(50,50,200,150);

//        this.add(roomNoLabel);
//        this.add(temperatureLabel);
//        this.add(statusLabel);
        this.add(button1);
        this.add(textArea);

        this.setLayout(null);

    }

    void addButton1Listener(ActionListener mal) {
        button1.addActionListener(mal);
    }

    public static void main(String[] args){
        UI_RoomStatus ui_comm = new UI_RoomStatus("0");
    }

}
