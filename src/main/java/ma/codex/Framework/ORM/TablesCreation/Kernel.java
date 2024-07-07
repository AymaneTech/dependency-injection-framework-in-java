package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Application;

public class Kernel {
    private final SchemaGenerator schemaGenerator;
    private final ConstraintManagement constraintManagement;
    private final QueryExecutor queryExecutor;

    public Kernel(SchemaGenerator generator, ConstraintManagement constraintManagement, QueryExecutor queryExecutor) {
        this.schemaGenerator = generator;
        this.constraintManagement = constraintManagement;
        this.queryExecutor = queryExecutor;
    }

    public void run() {
        try {
            schemaGenerator.generateSchema(Application.class);

            queryExecutor.execute(schemaGenerator.getSchemas());
            queryExecutor.execute(constraintManagement.getConstraints());
        } catch (Exception e) {
            System.err.println("error while creating tables");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
