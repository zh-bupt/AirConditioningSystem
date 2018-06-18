package server.mapper;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import server.DataBaseConnect;
import server.simpleclass.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillMapper implements IMapper {
    @Override
    public boolean insert(Object o) throws SQLServerException {
        Bill bill = (Bill)o;
        boolean result = true;
        Connection connection = DataBaseConnect.getConnection();
        if (connection != null) {
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            try {
                connection.setAutoCommit(false);
                String sql1 = String.format(
                        "insert into bill(electricity, cost, create_time) " +
                                "values(%f, %f, '%s')",
                        bill.getElectricity(), bill.getCost(), bill.getCreateTime()
                );
                ps1 = connection.prepareStatement(sql1);
                ps1.execute();
                // 获得自增下标
                int index;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select @@identity");
                if (resultSet.next()) index = resultSet.getInt(1);
                else throw new SQLException();
                if (statement != null) statement.close();
                if (resultSet != null) resultSet.close();

                String sql2 = String.format(
                        "insert into room_bill(room_id, bill_id) " +
                                "values(%s, %d)",
                        bill.getRoomId(), index
                );

                ps2 = connection.prepareStatement(sql2);
                ps2.execute();
                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                result = false;
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    if (ps1 != null) ps1.close();
                    if (ps2 != null) ps2.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
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
    public Object get(String condition) throws SQLServerException {
        Connection connection = DataBaseConnect.getConnection();
        if (connection == null) return null;
        String sql = "select room_id, electricity, cost, create_time from bill, room_bill " +
                "where bill.id = room_bill.bill_id and " + condition;
        Statement statement = null;
        ResultSet res = null;
        Bill bill = null;
        try {
            statement = connection.createStatement();
            res = statement.executeQuery(sql);
            String room_id, create_time;
            float electricity, cost;
            if (res.next()) {
                create_time = res.getString("create_time");
                room_id = res.getString("room_id");
                cost = res.getFloat("cost");
                electricity = res.getFloat("electricity");
                bill = new Bill(room_id);
                bill.setCost(cost);
                bill.setElectricity(electricity);
                bill.setCreateTime(create_time);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null) res.close();
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bill;
    }

    @Override
    public List gets(String condition) throws SQLServerException {
        Connection connection = DataBaseConnect.getConnection();
        if (connection == null) return null;
        String sql = "select room_id, electricity, cost," +
                " convert(varchar(100), create_time, 20) as create_time from bill, room_bill " +
                "where bill.id = room_bill.bill_id and " + condition;
        Statement statement = null;
        ResultSet res = null;
        List<Bill> list = new ArrayList<>();
        try {
            statement = connection.createStatement();
            res = statement.executeQuery(sql);
            String room_id, create_time;
            float electricity, cost;
            while (res.next()) {
                create_time = res.getString("create_time");
                room_id = res.getString("room_id");
                cost = res.getFloat("cost");
                electricity = res.getFloat("electricity");
                Bill bill = new Bill(room_id);
                bill.setCost(cost);
                bill.setElectricity(electricity);
                bill.setCreateTime(create_time);
                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null) res.close();
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
