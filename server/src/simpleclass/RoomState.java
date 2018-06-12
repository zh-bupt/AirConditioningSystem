package simpleclass;

import org.json.JSONException;
import org.json.JSONObject;


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
}
