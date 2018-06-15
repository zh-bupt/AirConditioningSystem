import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.*;

public class UI_RoomStatus_Controller {
    private UI_RoomStatus ui_roomStatus;
    RoomState roomState1;


    class RoomState {
        public String roomId = null;
        public int currentTemperature;
        public int targetTemperature;
        public String wind_power = null;
        public boolean isOn = false;

        public RoomState(String roomId,int currentTemperature,int targetTemperature,String wind_power,boolean isOn) {
            this.roomId = roomId;
            this.currentTemperature = currentTemperature;
            this.targetTemperature = targetTemperature;
            this.wind_power = wind_power;
            this.isOn = isOn;
        }
    }

    class Button1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ui_roomStatus.setTextArea("房间号："+roomState1.roomId+"\n现在温度："+roomState1.currentTemperature+"\n设定稳定："+roomState1.targetTemperature+"\n风速:"+roomState1.wind_power+"\n状态："+roomState1.isOn);
        }
    }

    public UI_RoomStatus_Controller(UI_RoomStatus roomStatus) {
        ui_roomStatus = roomStatus;
        ui_roomStatus.addButton1Listener(new Button1Listener());
        if(ui_roomStatus.getRoomNo()=="1"){
            int i=1;
            roomState1 = new RoomState(Integer.toString(i),30+i,20+i,"3",true);
            ui_roomStatus.setTextArea("房间号："+roomState1.roomId+"\n现在温度："+roomState1.currentTemperature+"\n设定稳定："+roomState1.targetTemperature+"\n风速:"+roomState1.wind_power+"\n状态："+roomState1.isOn);
        }else
        {
            int i=Integer.valueOf(ui_roomStatus.getRoomNo());
            roomState1 = new RoomState(Integer.toString(i),30+i,20+i,"3",true);
            ui_roomStatus.setTextArea("房间号："+roomState1.roomId+"\n现在温度："+roomState1.currentTemperature+"\n设定稳定："+roomState1.targetTemperature+"\n风速:"+roomState1.wind_power+"\n状态："+roomState1.isOn);
        }

        final int[] i = {1};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(100-i[0]>20)
                {
                    i[0]++;
                }
                roomState1 = new RoomState(ui_roomStatus.getRoomNo(),100-i[0],20,"3",true);
                ui_roomStatus.setTextArea("房间号："+roomState1.roomId+"\n现在温度："+roomState1.currentTemperature+"\n设定稳定："+roomState1.targetTemperature+"\n风速:"+roomState1.wind_power+"\n状态："+roomState1.isOn);
                System.out.println("Inside Timer Task" + System.currentTimeMillis());
            }
        };
        timer.schedule(task, 1000,1000);

    }


    public static void main(String[] args){
        UI_RoomStatus_Controller ui_con = new UI_RoomStatus_Controller(new UI_RoomStatus("1"));
    }
}

