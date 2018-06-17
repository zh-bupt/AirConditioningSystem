package server.simpleclass;

import server.StringUtils;
import server.TCPServer;

public class Bill {
    private String roomId;
    private float electricity;
    private float cost;
    private String createTime;

    public Bill(String roomId) {
        this.roomId = roomId;
        this.electricity = 0;
        this.cost = 0;
        this.createTime = StringUtils.getTimeString();
    }

    public void setElectricity(float electricity) {
        this.electricity = electricity;
        this.cost = TCPServer.getInstance().getPrice() * electricity;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public float getElectricity() {
        return electricity;
    }

    public float getCost() {
        return cost;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getRoomId() {
        return roomId;
    }
}
