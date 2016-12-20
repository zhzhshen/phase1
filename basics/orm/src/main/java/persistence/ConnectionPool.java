package persistence;

import config.ConnectionConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    public static Connection getConnection(ConnectionConfig connectionConfig) {
        try {
            return DriverManager.getConnection(connectionConfig.getBaseUrl() + "/" + connectionConfig.getDBName(),
                    connectionConfig.getUserName(),
                    connectionConfig.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
