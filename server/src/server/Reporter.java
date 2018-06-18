package server;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.mapper.BillMapper;
import server.mapper.RequestMapper;
import server.simpleclass.Bill;
import server.simpleclass.Request;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reporter {

    private String roomId;
    private String startTime;
    private String endTime;

    private float totalCost;
    private int startTimes;
    private List<Request> requestList;
    private List<Bill> billList;

    public Reporter(String roomId, String startTime, String endTime) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        billList = queryBills();
        requestList = queryRequests();
        totalCost = queryTotalCost();
        startTimes = queryStartTimes();
    }

    private List<Request> queryRequests(){
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


    private List<Bill> queryBills() {
        String condition = String.format(
                "room_id = '%s' " +
                        "and create_time > '%s' " +
                        "and create_time < '%s'",
                roomId, startTime, endTime
        );
        try {
            return new BillMapper().gets(condition);
        } catch (SQLServerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int queryStartTimes(){
        String SQL = String.format(
                "select count(distinct start_time) as start_times " +
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

    private float queryTotalCost() {
        String SQL = String.format(
                "select sum(cost) as total_cost " +
                        "from request, room_request " +
                        "where room_id = '%s' " +
                        "and id = request_id " +
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

    public List<Request> getRequestList() {
        return requestList;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public int getStartTimes() {
        return startTimes;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public boolean exportReport(){
        String reportSheetPath = "doc/" + roomId + "-" + startTime + "-" + endTime + ".txt";
        FileWriter writer = null;
        boolean result = false;
        try {
            writer = new FileWriter(reportSheetPath);
            writer.write(String.format("start times: %d\n", startTimes));
            writer.write(String.format("total cost: %f\n", totalCost));
            writer.write("request list:\n");
            writer.write("start_time\t\t\tend_time\t\t\tstart_temp\tend_temp\ttarget_temp\twind_power\tcost\telectricity\n");
            for (Request r : requestList) {
                writer.write(String.format(
                        "%s\t%s\t%d\t\t\t%d\t\t\t%d\t\t\t%s\t\t%f\t\t%f\n",
                        r.getStartTime(), r.getStopTime(), r.getStartTemp(), r.getEndTemp(), r.getTargetTemp(), r.getWindPower(), r.getCost(), r.getElectricity()
                ));
            }
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
