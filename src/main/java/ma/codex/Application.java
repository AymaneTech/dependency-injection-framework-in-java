package ma.codex;

import ma.codex.Framework.DIContext.Injector;
import ma.codex.Framework.ORM.TablesCreation.ConstraintManagement;
import ma.codex.Framework.ORM.TablesCreation.Kernel;
import ma.codex.Framework.ORM.TablesCreation.QueryExecutor;
import ma.codex.Framework.ORM.TablesCreation.SchemaGenerator;
import ma.codex.Framework.Utils.ScanByAnnotation;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Injector DIContext = new Injector();
        DIContext.scanClasses(Application.class);

        Kernel kernel = new Kernel(new ScanByAnnotation()
                , new SchemaGenerator()
                , new ConstraintManagement()
                , new QueryExecutor()
        );

        kernel.run();
    }
}
