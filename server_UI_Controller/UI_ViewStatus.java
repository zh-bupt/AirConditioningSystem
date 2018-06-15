import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_ViewStatus extends JFrame implements ActionListener{
    private JTextArea textArea1;
    public UI_ViewStatus(){
        init();
        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init(){
        this.setTitle("查看从机状态");

        textArea1 = new JTextArea("");
        textArea1.setBounds(10,10, 280,260);
        this.add(textArea1);

        this.setLayout(null);

    }

    public void actionPerformed(ActionEvent e){
        JOptionPane.showMessageDialog(this,"设置成功！");
    }

    public static void main(String[] args){
        UI_ViewStatus ui_comm = new UI_ViewStatus();
    }

}
