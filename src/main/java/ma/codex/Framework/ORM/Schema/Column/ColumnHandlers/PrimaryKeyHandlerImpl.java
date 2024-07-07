package ma.codex.Framework.ORM.Schema.Column.ColumnHandlers;

import ma.codex.Framework.Persistence.Annotations.Column;

import java.lang.reflect.Field;

public final class PrimaryKeyHandlerImpl implements ColumnHandler {
    @Override
    public String handle(Field field) {
        return "";
    }
}
