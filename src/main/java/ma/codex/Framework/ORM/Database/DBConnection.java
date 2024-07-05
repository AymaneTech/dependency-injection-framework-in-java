package ma.codex.Framework.ORM.Database;


import ma.codex.Framework.Utils.env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance = null;
    private Connection connection = null;

    private DBConnection() throws SQLException {
        init();
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null || !instance.connection.isClosed()) {
            instance = new DBConnection();
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
        String url = env.get("DB_URL");
        String username = env.get("DB_USERNAME");
        String password = env.get("DB_PASSWORD");
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
