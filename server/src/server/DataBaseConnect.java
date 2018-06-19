package server;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseConnect {
    private static String user = "sa";
    private static String database = "HotelSystem";
//    private static String password = "94@Docker-mssql";
//    private static String address = "//localhost:1433";
    private static String password = "123456";
    private static String address = "//10.206.18.243:1433";
    private static String driver = "jdbc:sqlserver";

    /*
     * @Description 获得数据库连接
     * @Param void
     * @Return Connection 数据库连接
     */
    public static Connection getConnection() {
        String connectionUrl = driver + ":" + address + ";" + "databaseName=" + database;

        Connection con = null;
        try {
            // Establish the connection.
            con = DriverManager.getConnection(connectionUrl, user, password);
            return con;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * @Description 获取查询语句的结果, 存储在 ArrayList 中
     * @Param querySQL SQL语句
     * @Return ArrayList<HashMap<String, String>> 查询结果
     */
    public static ArrayList<HashMap<String, String>> query(String querySQL) {
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<HashMap<String, String>> resultList = null;
                new ArrayList<HashMap<String, String>>();
        if (con != null) {
            try {
                resultList = new ArrayList<>();
                stmt = con.createStatement();
                rs = stmt.executeQuery(querySQL);
                ResultSetMetaData metaData = rs.getMetaData();
                while (rs.next()) {
                    HashMap<String, String> tuple = new HashMap<>();
                    for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                        String columnName = metaData.getColumnName(i);
                        String data = rs.getString(i);
                        System.out.println(columnName + ":" + data);
                        tuple.put(columnName, data);
                    }
                    resultList.add(tuple);
                }
                return resultList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }finally {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (stmt != null) try { stmt.close(); } catch(Exception e) {}
                if (con != null) try { con.close(); } catch(Exception e) {}
            }
        }
        return null;
    }

    public static boolean noneQuery(String SQL) {
        Connection con = getConnection();
        Statement stmt = null;
        boolean result = false;

        if (con != null) {
            try {
                stmt = con.createStatement();
                result = stmt.execute(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if (stmt != null) try { stmt.close(); } catch(Exception e) {}
                if (con != null) try { con.close(); } catch(Exception e) {}
            }
        }
        return result;
    }
}
