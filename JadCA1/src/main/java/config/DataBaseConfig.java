package config;
import java.sql.*;

public class DataBaseConfig {
	private static final String JDBC_URL = "jdbc:mysql://jadbookstoredb.cpjd7st4o4nt.us-east-2.rds.amazonaws.com:3306/jadBookStore";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1392001ksp";

    private DataBaseConfig() {
        // private constructor to prevent instantiation
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
