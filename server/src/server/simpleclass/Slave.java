package server.simpleclass;

/*
 * Slave: 从机类，包含从机开机的时间
 */
public class Slave {
    private String roomId;
    private String startTime;

    public Slave(String room_id, String start_time) {
        this.roomId = room_id;
        this.startTime = start_time;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getStartTime() {
        return startTime;
    }
}
