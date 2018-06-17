package server;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.mapper.BillMapper;
import server.mapper.RequestMapper;
import server.simpleclass.Bill;
import server.simpleclass.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reporter {

    private String roomId;
    private String startTime;
    private String endTime;

    public Reporter(String roomId, String startTime, String endTime) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<Request> getRequestList(){
        String condition = String.format(
                "room_id = '%s' " +
                        "and start_time > '%s' " +
                        "and start_time < '%s'",
                roomId, startTime, endTime
        );
        try {
            return new RequestMapper().gets(condition);
        } catch (SQLServerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Bill> getBillList() {
        String condition = String.format(
                "room_id = '%s' " +
                        "and start_time > '%s' " +
                        "and start_time < '%s'",
                roomId, startTime, endTime
        );
        try {
            return new BillMapper().gets(condition);
        } catch (SQLServerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getStartTimes(){
        String SQL = String.format(
                "select count(distinct start_time) as start_times" +
                        "from slave " +
                        "where room_id = '%s' " +
                        "and start_time > '%s' " +
                        "and start_time < '%s'",
                roomId, startTime, endTime
        );
        ArrayList<HashMap<String, String>> list = DataBaseConnect.query(SQL);
        if (list == null || list.size() == 0) return 0;
        HashMap<String, String> map = list.get(0);
        int times;
        try {
            times = Integer.parseInt(map.get("start_times"));
        } catch (NumberFormatException e){
            times = 0;
            e.printStackTrace();
        }
        return times;
    }

    public float getTotalCost() {
        String SQL = String.format(
                "select sum(cost) as total_cost " +
                        "from request " +
                        "where room_id = '%s' " +
                        "and start_time > '%s' " +
                        "and start_time < '%s'",
                roomId, startTime, endTime
        );
        ArrayList<HashMap<String, String>> list = DataBaseConnect.query(SQL);
        if (list == null || list.size() == 0) return 0;
        HashMap<String, String> map = list.get(0);
        float total_cost;
        try {
            total_cost = Float.parseFloat(map.get("total_cost"));
        } catch (NumberFormatException e){
            total_cost = 0;
            e.printStackTrace();
        }
        return total_cost;
    }

    // TODO 导出报表
    public boolean exportReport(){
        return false;
    }
}
