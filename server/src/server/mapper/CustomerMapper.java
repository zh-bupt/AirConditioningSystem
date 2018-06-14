package server.mapper;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.DataBaseConnect;
import server.simpleclass.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerMapper implements IMapper {
    @Override
    public boolean insert(Object o) throws SQLServerException {
        Customer customer = (Customer)o;
        String id = customer.getId();
        String room_id = customer.getRoom_id();
        String SQL =
                String.format("insert customer(id, room_id) values('%s','%s')", id, room_id);
        return DataBaseConnect.noneQuery(SQL);
    }

    @Override
    public boolean delete(String condition) {
        return DataBaseConnect.noneQuery(condition);
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public Object get(String condition) {
        List<Customer> list = (List<Customer>) gets(condition);
        if (list ==null || list.size() == 0) return null;
        return list.get(0);
    }

    @Override
    public List gets(String condition) {
        List<HashMap<String, String>> list = DataBaseConnect.query(condition);
        List<Customer> customerList = new ArrayList<>();
        for (HashMap<String, String> map : list) {
            Customer customer = new Customer(map);
            customerList.add(customer);
        }
        return customerList;
    }
}
