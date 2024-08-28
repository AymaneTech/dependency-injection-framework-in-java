package ma.codex.framework.orm.entityManager.core;

import ma.codex.framework.iocContainer.annotations.Autowired;
import ma.codex.framework.iocContainer.annotations.Component;
import ma.codex.framework.orm.persistence.annotations.Entity;
import ma.codex.framework.orm.entityManager.schema.constraints.ConstraintManager;
import ma.codex.framework.orm.entityManager.schema.tables.SchemaGenerator;
import ma.codex.framework.utils.ScanByAnnotation;

import java.util.Collection;

@Component
public class ORMKernel {
    private final ScanByAnnotation scanByAnnotation;
    private final SchemaGenerator schemaGenerator;
    private final ConstraintManager constraintManagement;
    private final QueryExecutor queryExecutor;
    private String packageName;

    @Autowired
    public ORMKernel(ScanByAnnotation scanByAnnotation, SchemaGenerator generator, ConstraintManager constraintManagement, QueryExecutor queryExecutor) {
        this.scanByAnnotation = scanByAnnotation;
        this.schemaGenerator = generator;
        this.constraintManagement = constraintManagement;
        this.queryExecutor = queryExecutor;
    }

    public void setPackageName(Class<?> mainClass) {
        this.packageName = mainClass.getPackage().getName().replace(".", "/");
    }

    public void run() {
        try {
            scanByAnnotation.setAnnotation(Entity.class);
            final Collection<Class<?>> entityClasses = scanByAnnotation.find(packageName);
            schemaGenerator.setSchemas(entityClasses);
            constraintManagement.setConstraints(entityClasses);

            queryExecutor.execute(schemaGenerator.getSchemas());
            queryExecutor.execute(constraintManagement.getConstraints());
        } catch (Exception e) {
            System.err.println("error occurred in the ORM kernel");
            System.err.println(e.getMessage());
        }
    }
}
