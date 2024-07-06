package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Application;
import ma.codex.Framework.ORM.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableCreator {
    private final SchemaGenerator generator;
    private final Connection connection;

    public TableCreator(SchemaGenerator generator) throws SQLException {
        this.generator = generator;
        connection = DBConnection.getInstance().getConnection();
    }

    public void createTables() throws SQLException {
        List<String> schemas = generator.generateTableCreationQueries(Application.class);
        System.out.println(schemas);
        try {
            for (String schema : schemas) {
                PreparedStatement stmt = connection.prepareStatement(schema);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("error while creating tables");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
