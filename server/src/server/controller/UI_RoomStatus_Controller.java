package server.controller;

import server.manager.StateManager;
import server.simpleclass.RoomState;
import server.ui.UI_RoomStatus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.*;


public class UI_RoomStatus_Controller {
    private UI_RoomStatus ui_roomStatus;
    RoomState roomState;

    class Button1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            roomState = StateManager.getInstance().getState(ui_roomStatus.getRoomNo());
            ui_roomStatus.setTextArea("房间号："+roomState.getRoomId()+"\n现在温度："+roomState.getCurrentTemperature()+"\n设定稳定："+roomState.getTargetTemperature()+"\n风速:"+roomState.getWind_power()+"\n状态："+ roomState.isOn());


        }
    }

    public UI_RoomStatus_Controller(UI_RoomStatus roomStatus) {
        ui_roomStatus = roomStatus;
        roomState = StateManager.getInstance().getState(ui_roomStatus.getRoomNo());
        ui_roomStatus.setTextArea("房间号："+roomState.getRoomId()+"\n现在温度："+roomState.getCurrentTemperature()+"\n设定稳定："+roomState.getTargetTemperature()+"\n风速:"+roomState.getWind_power()+"\n状态："+ roomState.isOn());
        ui_roomStatus.addButton1Listener(new Button1Listener());


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                roomState = StateManager.getInstance().getState(ui_roomStatus.getRoomNo());
                ui_roomStatus.setTextArea("房间号："+roomState.getRoomId()+"\n现在温度："+roomState.getCurrentTemperature()+"\n设定稳定："+roomState.getTargetTemperature()+"\n风速:"+roomState.getWind_power()+"\n状态："+ roomState.isOn());
            }
        };
        timer.schedule(task, 1000,1000);

    }


    public static void main(String[] args){
        UI_RoomStatus_Controller ui_con = new UI_RoomStatus_Controller(new UI_RoomStatus("1001"));
    }
}

