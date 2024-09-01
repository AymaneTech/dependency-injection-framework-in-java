package ma.codex.framework.orm.entityManager.schema.tables.columnHandlers;

import java.lang.reflect.Field;

public final class PrimaryKeyHandlerImpl implements ColumnHandler {
    @Override
    public String handle(final Field field) {
        return String.format("    %s SERIAL PRIMARY KEY", field.getName());
    }
}
