package ma.codex.Framework.ORM.Schema.Column.ColumnHandlers;

import java.lang.reflect.Field;

public final class PrimaryKeyHandlerImpl implements ColumnHandler {
    @Override
    public String handle(Field field) {
        return String.format("    %s SERIAL PRIMARY KEY", field.getName());
    }
}
