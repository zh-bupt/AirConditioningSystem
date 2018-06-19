package server.simpleclass;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * RoomState: 房间状态类，包含一个房间的温度信息等
 */
public class RoomState {
    private String roomId = null;
    private int currentTemperature;
    private int targetTemperature;
    private String wind_power = null;
    private boolean isOn = false;

    public RoomState(JSONObject jsonObject, String roomId) throws JSONException {
        this.roomId = roomId;
        this.currentTemperature = jsonObject.getInt("current_tmp");
        this.targetTemperature = jsonObject.getInt("target_tmp");
        this.wind_power = jsonObject.getString("wind_power");
    }

    public RoomState(String roomId) {
        this.roomId = roomId;
        this.currentTemperature = 0;
        this.targetTemperature = 0;
        this.wind_power = "low";
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }
    public String getRoomId() {
        return roomId;
    }

    public int getTargetTemperature() {
        return targetTemperature;
    }

    public String getWind_power() {
        return wind_power;
    }

    public boolean isOn() {
        return isOn;
    }


}
