package ma.codex.Framework.ORM.Schema.Column.ColumnHandlers;

import ma.codex.Framework.Persistence.Annotations.Column;
import ma.codex.Framework.Persistence.Annotations.ID;

import java.lang.reflect.Field;

public final class ColumnHandlerImpl implements ColumnHandler {
    @Override
    public String handle(Field field) {
        Column column = field.getAnnotation(Column.class);
        String type = determineColumnType(field, column);
        String isNull = column.nullable() ? "" : "NOT NULL";
        return String.format("  %s %s %s", column.name(), type, isNull);
    }

    private String getColumnDefinition(Field field) {
        if (field.isAnnotationPresent(ID.class)) {
            return String.format("    %s SERIAL PRIMARY KEY", field.getName());
        } else if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);

            return String.format("    %s %s %s", column.name(), type, isNull);
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
