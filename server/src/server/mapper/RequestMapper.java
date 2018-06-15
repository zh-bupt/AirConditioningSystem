package server.mapper;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.DataBaseConnect;
import server.simpleclass.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestMapper implements IMapper {
    @Override
    public boolean insert(Object o) throws SQLServerException {
        Request request = (Request) o;
        String room_id = request.getRoomId();
        int start_temp = request.getStartTemp();
        int end_temp = request.getEndTemp();
        int target_temp = request.getTargetTemp();
        String start_time = request.getStartTime();
        String stop_time = request.getStopTime();
        float cost = request.getCost();
        String wind_power = request.getWindPower();
        float electricity = request.getElectricity();
        String SQL = String.format(
                "insert request(room_id, start_time, stop_time, start_temp, end_temp, target_temp, wind_power, cost, electricity) " +
                        "values ('%s', '%s', '%s', %d, %d, %d, '%s', %f, %f)",
                room_id,
                start_time,
                stop_time,
                start_temp,
                end_temp,
                target_temp,
                wind_power,
                cost,
                electricity
                );
        return DataBaseConnect.noneQuery(SQL);
    }

    @Override
    public boolean delete(String condition) {
        return false;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public Object get(String condition) {
        return null;
    }

    @Override
    public List gets(String condition) {
        List<HashMap<String, String>> list = DataBaseConnect.query(condition);
        List<Request> requestList = new ArrayList<>();
        for (HashMap<String, String> map : list) {
            Request request = new Request(map);
            requestList.add(request);
        }
        return requestList;
    }
}
