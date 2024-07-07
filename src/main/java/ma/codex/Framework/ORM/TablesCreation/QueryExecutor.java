package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Framework.ORM.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QueryExecutor {
    private final Connection connection;

    public QueryExecutor() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    public void execute(List<String> schemas) throws SQLException {
        try {
            for (String schema : schemas) {
                try (PreparedStatement stmt = connection.prepareStatement(schema)) {
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("error while creating tables");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
