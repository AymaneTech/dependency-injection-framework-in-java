package ma.codex.framework;

import java.sql.SQLException;

import ma.codex.framework.iocContainer.Injector;
import ma.codex.framework.orm.entityManager.core.OrmKernel;
import ma.codex.framework.orm.entityManager.core.QueryExecutor;
import ma.codex.framework.orm.entityManager.schema.constraints.ConstraintManager;
import ma.codex.framework.orm.entityManager.schema.tables.SchemaGenerator;
import ma.codex.framework.utils.ScanByAnnotation;

public class Kernel {

    public static void run(Class<?> mainClass) throws SQLException {
        Injector DIContext = new Injector(new ScanByAnnotation(), mainClass);
        DIContext.run();

        // OrmKernel ormKernel = DIContext.getComponent(OrmKernel.class);
        OrmKernel ormKernel = new OrmKernel(new ScanByAnnotation(), new SchemaGenerator(), new ConstraintManager(), new QueryExecutor());
        ormKernel.setPackageName(mainClass);
        ormKernel.run();
    }
}
