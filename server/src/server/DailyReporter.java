package server;

import server.mapper.RequestMapper;

import java.util.List;

public class DailyReporter extends Reporter {
//    private String room_id;
//    private String day;
//
//    public DailyReporter(String room_id, String day) {
//        this.room_id = room_id;
//        this.day = day;
//    }
//
//    public List getRequestList() {
//        String SQL = String.format(
//                "select * from request " +
//                        "where room_id = '%s' " +
//                        "and start_time > '%s 00:00:00' " +
//                        "and start_time < '%s 23:59:59'",
//                room_id,
//                day, day
//        );
//        return new RequestMapper().gets(SQL);
//    }
//
//    public int getStartTimes() {
//        return 0;
//    }
//
//    @Override
//    public boolean exportReport() {
//        return false;
//    }
}
