package ma.codex;

import ma.codex.Framework.DIContext.Injector;
import ma.codex.Framework.ORM.TablesCreation.SchemaGenerator;
import ma.codex.Framework.ORM.TablesCreation.QueryExecutor;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Injector DIContext = new Injector();
        DIContext.scanClasses(Application.class);

        QueryExecutor creator = new QueryExecutor(new SchemaGenerator());
        creator.execute();
    }
}
