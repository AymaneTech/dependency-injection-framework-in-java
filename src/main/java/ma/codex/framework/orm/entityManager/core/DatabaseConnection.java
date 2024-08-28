package ma.codex.framework.ORM.ShcemaManager.Core;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ma.codex.framework.Utils.env;

public class DatabaseConnection {

    private static DatabaseConnection instance = null;
    private Connection connection = null;

    private DatabaseConnection() throws SQLException {
        init();
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || !instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static boolean closeConnection() {
        if (instance != null) {
            try {
                instance.getConnection().close();
                instance = null;
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private void init() throws SQLException {
        final String url = env.get("DB_URL");
        final String username = env.get("DB_USERNAME");
        final String password = env.get("DB_PASSWORD");
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
