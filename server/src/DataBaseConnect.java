import java.sql.*;

public class DataBaseConnect {
    private static String user = "sa";
    private static String database = "HotelSystem";
    private static String password = "94@Docker-mssql";
    private static String address = "//localhost:1433";
    private static String driver = "jdbc:sqlserver";


    public static Connection getConnection() {
//        String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
//                "databaseName=HotelSystem;";
        String connectionUrl = driver + ":" + address + ";" + "databaseName=" + database;

        // Declare the JDBC objects.
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

    public static ResultSet query(String querySQL) {
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        if (con != null) {
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(querySQL);
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }finally {
//                if (stmt != null) try { stmt.close(); } catch(Exception e) {}
//                if (con != null) try { con.close(); } catch(Exception e) {}
            }
        }
        return null;
    }
}
