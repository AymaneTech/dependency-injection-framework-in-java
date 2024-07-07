package ma.codex;

import ma.codex.Framework.DIContext.Injector;
import ma.codex.Framework.ORM.Schema.Constraint.ConstraintManager;
import ma.codex.Framework.ORM.Core.ORMKernel;
import ma.codex.Framework.ORM.Core.QueryExecutor;
import ma.codex.Framework.ORM.Schema.SchemaGenerator;
import ma.codex.Framework.Utils.ScanByAnnotation;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Injector DIContext = new Injector();
        DIContext.scanClasses(Application.class);

        ORMKernel kernel = new ORMKernel(new ScanByAnnotation()
                , new SchemaGenerator()
                , new ConstraintManager()
                , new QueryExecutor()
        );

        kernel.run();
    }
}
