package ma.codex.Framework.ORM.ShcemaManager.Core;

import org.postgresql.util.PSQLException;

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
        for (String schema : schemas) {
            if (!schema.isEmpty()) {
                try (PreparedStatement stmt = connection.prepareStatement(schema)) {
                    stmt.executeUpdate();
                } catch (PSQLException e) {
                    if (e.getMessage().contains("already exists")) {
                        continue;
                    }
                    System.err.println("error in statement execution");
                    System.err.println(e.getMessage());
                    System.err.println("schema: " + schema);
                }
            }
        }
    }
}
