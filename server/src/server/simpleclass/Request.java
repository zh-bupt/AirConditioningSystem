package server.simpleclass;

import org.json.JSONException;
import org.json.JSONObject;
import server.TCPServer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/*
 * Request: 温控请求类，包含一次温控请求恩基本信息
 */
public class Request {
    private String roomId = "";
    private String startTime  = "";
    private String stopTime = "";
    private int startTemp = 0;
    private int endTemp = 0;
    private int targetTemp = 0;
    private String windPower = "";
    private float cost = 0;
    private float electricity = 0;

    public Request(String roomId, String startTime, int startTemp, String windPower) {
        this.cost = 0;
        this.roomId = roomId;
        this.startTime = startTime;
        this.startTemp = startTemp;
        this.windPower = windPower;
    }

    // TODO
    public Request(Map<String, String> map) {
        System.out.println(map.toString());
        try {
            String room_id = map.get("room_id");
            if (room_id != null) this.roomId = room_id;
            else this.roomId = "9999";
            String start_time = map.get("start_time");
            if (start_time != null) this.startTime = start_time;
            else this.startTime = "1990-01-01 00:00:00";
            String stop_time = map.get("stop_time");
            if (stop_time != null) this.stopTime = stop_time;
            else this.stopTime = "1990-01-01 00:00:00";
            String start_temp = map.get("start_temp");
            if (start_temp != null) this.startTemp = Integer.valueOf(start_temp);
            else this.startTemp = 0;
            String end_temp = map.get("end_temp");
            if (end_temp != null) this.endTemp = Integer.valueOf(end_temp);
            else this.endTemp = 0;
            String target_temp = map.get("target_temp");
            if (target_temp != null) this.targetTemp = Integer.valueOf(target_temp);
            else this.targetTemp = 0;
            String wind_power = map.get("wind_power");
            if (wind_power != null) this.windPower = wind_power;
            else this.windPower = "low";
            String elec = map.get("electricity");
            if (elec != null) this.electricity = Float.valueOf(elec);
            else this.electricity = 0;
            this.cost = TCPServer.getInstance().getPrice() * electricity;
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public Request(JSONObject jsonObject, String roomId) throws JSONException {
        this.roomId = roomId;
        this.startTemp = jsonObject.getInt("current_tmp");
        this.targetTemp = jsonObject.getInt("target_tmp");
        this.windPower = jsonObject.getString("wind_power");
        this.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void setEndTemp(int endTemp) {
        this.endTemp = endTemp;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setStartTemp(int startTemp) {
        this.startTemp = startTemp;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getWindPower() {
        return windPower;
    }

    public String getRoomId() {
        return roomId;
    }

    public float getCost() {
        return cost;
    }

    public int getEndTemp() {
        return endTemp;
    }

    public int getStartTemp() {
        return startTemp;
    }

    public int getTargetTemp() {
        return targetTemp;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public float getElectricity() {
        return electricity;
    }

    public void setElectricity(float electricity) {
        this.electricity = electricity;
        this.cost = electricity * TCPServer.getInstance().getPrice();
    }
}
