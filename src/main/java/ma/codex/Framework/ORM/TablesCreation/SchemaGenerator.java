package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Framework.ORM.Persistence.Annotations.Column;
import ma.codex.Framework.ORM.Persistence.Annotations.Entity;
import ma.codex.Framework.ORM.Persistence.Annotations.ID;
import ma.codex.Framework.Utils.ScanByAnnotation;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SchemaGenerator {

    public List<String> generateTableCreationQueries(Class<?> mainClass) {
        String packageName = mainClass.getPackage().getName().replace(".", "/");
        ScanByAnnotation scanner = new ScanByAnnotation(Entity.class);
        Collection<Class<?>> entityClasses = scanner.find(packageName);

        return entityClasses.stream()
                .map(this::generateTableCreationQuery)
                .collect(Collectors.toList());
    }

    private String generateTableCreationQuery(Class<?> entityClass) {
        String tableName = entityClass.getAnnotation(Entity.class).name();
        String columnDefinitions = getColumnDefinitions(entityClass);

        return String.format("""
                CREATE TABLE IF NOT EXISTS "%s" (
                %s,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    deleted_at TIMESTAMP
                );
                            
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'update_%s_updated_at') THEN
                        CREATE TRIGGER update_%s_updated_at
                        BEFORE UPDATE ON "%s"
                        FOR EACH ROW
                        EXECUTE FUNCTION update_updated_at_column();
                    END IF;
                END $$;
                            
                CREATE OR REPLACE FUNCTION update_updated_at_column()
                RETURNS TRIGGER AS $$
                BEGIN
                    NEW.updated_at = CURRENT_TIMESTAMP;
                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;
                """, tableName, columnDefinitions, tableName.toLowerCase(), tableName.toLowerCase(), tableName);
    }

    private String getColumnDefinitions(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .map(this::getColumnDefinition)
                .filter(def -> !def.isEmpty())
                .collect(Collectors.joining(",\n"));
    }

    private String getColumnDefinition(Field field) {
        if (field.isAnnotationPresent(ID.class)) {
            return String.format("    %s SERIAL PRIMARY KEY", field.getName());
        } else if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            String type = determineColumnType(field, column);
            String isNull = column.nullable() ? "" : "NOT NULL";
            return String.format("    %s %s %s", field.getName(), type, isNull);
        }
        return "";
    }

    private String determineColumnType(Field field, Column column) {
        if (!column.type().isEmpty()) {
            return column.type();
        } else if (column.size() != 0 && field.getType() == String.class) {
            return String.format("VARCHAR(%d)", column.size());
        } else {
            return getColumnType(field.getType());
        }
    }

    public String getColumnType(Class<?> javaType) {
        return switch (javaType.getSimpleName()) {
            case "int", "Integer" -> "INTEGER";
            case "long", "Long" -> "BIGINT";
            case "String" -> "VARCHAR(255)";
            case "double", "Double" -> "DOUBLE PRECISION";
            case "float", "Float" -> "REAL";
            case "boolean", "Boolean" -> "BOOLEAN";
            default -> throw new IllegalArgumentException("Unsupported type: " + javaType.getSimpleName());
        };
    }
}