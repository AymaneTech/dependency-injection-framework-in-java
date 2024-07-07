package ma.codex.Framework.ORM.Core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QueryExecutor {
    private final Connection connection;

    public QueryExecutor() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void execute(List<String> schemas) throws SQLException {
        try {
            for (String schema : schemas) {
                if (!schema.isEmpty()) {
                    try (PreparedStatement stmt = connection.prepareStatement(schema)) {
                        stmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("error in statement execution");
            System.err.println(e.getMessage());
//            e.printStackTrace();
        }
    }
}
