package ma.codex.Framework.ORM.Core;

import ma.codex.Application;
import ma.codex.Framework.ORM.Schema.Constraint.ConstraintManager;
import ma.codex.Framework.ORM.Schema.SchemaGenerator;
import ma.codex.Framework.Persistence.Annotations.Entity;
import ma.codex.Framework.Utils.ScanByAnnotation;

import java.util.Collection;

public class ORMKernel {
    private final ScanByAnnotation scanByAnnotation;
    private final SchemaGenerator schemaGenerator;
    private final ConstraintManager constraintManagement;
    private final QueryExecutor queryExecutor;

    public ORMKernel(ScanByAnnotation scanByAnnotation, SchemaGenerator generator, ConstraintManager constraintManagement, QueryExecutor queryExecutor) {
        this.scanByAnnotation = scanByAnnotation;
        this.schemaGenerator = generator;
        this.constraintManagement = constraintManagement;
        this.queryExecutor = queryExecutor;
    }

    public void run() {
        String packageName = Application.class.getPackage().getName().replace(".", "/");
        try {
            scanByAnnotation.setAnnotation(Entity.class);
            Collection<Class<?>> entityClasses = scanByAnnotation.find(packageName);

            schemaGenerator.generateSchema(entityClasses);
            constraintManagement.setConstraints(entityClasses);


            queryExecutor.execute(schemaGenerator.getSchemas());
            queryExecutor.execute(constraintManagement.getConstraints());
        } catch (Exception e) {
            System.err.println("error while creating tables");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
