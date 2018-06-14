package server;

import server.mapper.RequestMapper;
import server.simpleclass.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reporter {

    public static List<Request> getRequestList(String room_id, String start_time, String end_time){
        String SQL = String.format(
                "select * from request " +
                        "where room_id = '%s' " +
                        "and start_time > '%s' " +
                        "and start_time < '%s'",
                room_id, start_time, end_time
        );
        return new RequestMapper().gets(SQL);
    }

    public static int getStartTimes(String room_id, String start_time, String end_time){
        String SQL = String.format(
                "select count(distinct start_time) as start_times" +
                        "from slave " +
                        "where room_id = '%s' " +
                        "and start_time > '%s' " +
                        "and start_time < '%s'",
                room_id, start_time, end_time
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

    public static float getTotalCost(String room_id, String start_time, String end_time) {
        String SQL = String.format(
                "select sum(cost) as total_cost " +
                        "from request " +
                        "where room_id = '%s' " +
                        "and start_time > '%s' " +
                        "and start_time < '%s'",
                room_id, start_time, end_time
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

    public static boolean exportReport(){
        return false;
    }
}
