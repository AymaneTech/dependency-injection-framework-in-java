package ma.codex.Framework.ORM.ShcemaManager.Schema.Tables.ColumnHandlers;

import java.lang.reflect.Field;

import ma.codex.Framework.ORM.Persistence.Annotations.Column;

public final class ColumnHandlerImpl implements ColumnHandler {
    @Override
    public String handle(final Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            final Column column = field.getAnnotation(Column.class);
            final String type = determineColumnType(field, column);
            final String isNull = column.nullable() ? "" : "NOT NULL";
            return String.format("  %s %s %s", column.name(), type, isNull);
        }
        return "";
    }

    private String determineColumnType(final Field field, final Column column) {
        try {
            if (!column.type().isBlank()) {
                return column.type();
            } else if (column.size() != 0 && field.getType() == String.class) {
                return String.format("VARCHAR(%d)", column.size());
            } else {
                return getColumnType(field.getType());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported type: " + field.getType().getSimpleName());
        }
    }

    private String getColumnType(Class<?> javaType) {
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
