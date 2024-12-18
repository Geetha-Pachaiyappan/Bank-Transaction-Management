package bank;

import java.sql.*;
import java.sql.SQLDataException;

public class ConnectionDao {

    private static final String url = "jdbc:mysql://localhost:3306/new_bank";
    private static final String userName = "root";
    private static final String password = "VarunSai@03";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,userName,password);
    }
}
