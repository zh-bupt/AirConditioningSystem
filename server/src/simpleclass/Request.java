package simpleclass;

import org.json.JSONException;
import org.json.JSONObject;


/*
 * Request: 温控请求类，包含一次温控请求恩基本信息
 */
public class Request {
    private String roomId = null;
    private String startTime  = null;
    private String stopTime = null;
    private int startTemp;
    private int endTemp;
    private int targetTemp;
    private String windPower = null;
    private float cost;

    public Request(String roomId, String startTime, int startTemp, String windPower) {
        this.cost = 0;
        this.roomId = roomId;
        this.startTime = startTime;
        this.startTemp = startTemp;
        this.windPower = windPower;
    }

    // TODO: 时间的确定，是使用实际时间还是模拟时间
    public Request(JSONObject jsonObject, String roomId) throws JSONException {
        this.roomId = roomId;
        this.startTemp = jsonObject.getInt("current_tmp");
        this.targetTemp = jsonObject.getInt("target_tmp");
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
}
