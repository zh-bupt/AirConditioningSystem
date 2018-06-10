package simpleclass;

public class Request {
    private String roomId = null;
    private String startTime  = null;
    private String stopTime = null;
    private int startTemp;
    private int endTemp;
    private String windPower = null;
    private float cost;

    public Request(String roomId, String startTime, int startTemp, String windPower) {
        this.cost = 0;
        this.roomId = roomId;
        this.startTime = startTime;
        this.startTemp = startTemp;
        this.windPower = windPower;
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
}
