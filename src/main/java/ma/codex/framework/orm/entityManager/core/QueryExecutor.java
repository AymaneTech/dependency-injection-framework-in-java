package ma.codex.framework.orm.entityManager.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.postgresql.util.PSQLException;

public class QueryExecutor {
    private final Connection connection;

    public QueryExecutor() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    /*
    * This method used to execute a list of queries for create, alter, drop, etc.
    * */
    public void execute(final List<String> schemas) throws SQLException {
        for (final String schema : schemas) {
            if (!schema.isEmpty()) {
                try (final PreparedStatement stmt = connection.prepareStatement(schema)) {
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

    /*
    * I'll use this for select queries
    * */
    public ResultSet execute(final String query) {
        try (final Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("error in statement execution");
            System.err.println(e.getMessage());
            System.err.println("query: " + query);
        }
        return null;
    }

    /*
    * I'll use this for update and insert queries
    * */
/*
    public int execute(PreparedStatement stmt) {
        try ()
    }
*/


}
