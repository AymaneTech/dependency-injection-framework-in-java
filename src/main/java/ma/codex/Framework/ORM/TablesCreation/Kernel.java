package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Application;
import ma.codex.Framework.ORM.Persistence.Annotations.Entity;
import ma.codex.Framework.Utils.ScanByAnnotation;

import java.util.Collection;

public class Kernel {
    private final ScanByAnnotation scanByAnnotation;
    private final SchemaGenerator schemaGenerator;
    private final ConstraintManagement constraintManagement;
    private final QueryExecutor queryExecutor;

    public Kernel(ScanByAnnotation scanByAnnotation, SchemaGenerator generator, ConstraintManagement constraintManagement, QueryExecutor queryExecutor) {
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
            constraintManagement.generateSchema(entityClasses);

//            System.out.println(schemaGenerator.getSchemas());
//            System.out.println(constraintManagement.getConstraints());


            queryExecutor.execute(schemaGenerator.getSchemas());
            queryExecutor.execute(constraintManagement.getConstraints());
        } catch (Exception e) {
            System.err.println("error while creating tables");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
