package server.mapper;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.DataBaseConnect;
import server.simpleclass.Slave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlaveMapper implements IMapper {
    @Override
    public boolean insert(Object o) throws SQLServerException {
        Slave slave = (Slave)o;
        String SQL =
                String.format("insert slave(id, room_id) values('%s','%s')", slave.getRoomId(), slave.getStartTime());
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
        ArrayList<HashMap<String, String>> list = DataBaseConnect.query(condition);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
//        Integer integer = null;
//        for (String key : map.keySet()) {
//            String result = map.get(key);
//            try {
//                integer = Integer.valueOf(result);
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//        return integer;
    }

    @Override
    public List gets(String condition) {
        return null;
    }
}
