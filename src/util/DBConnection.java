package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:ORCL";

    private static final String USER = "scott";     // same as SQL*Plus
    private static final String PASSWORD = "tiger"; // your password

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con =DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Oracle DB Connected Successfully");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

