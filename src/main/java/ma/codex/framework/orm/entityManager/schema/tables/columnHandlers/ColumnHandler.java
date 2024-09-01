package ma.codex.framework.orm.entityManager.schema.tables.columnHandlers;

import java.lang.reflect.Field;

public sealed interface ColumnHandler permits ColumnHandlerImpl, PrimaryKeyHandlerImpl{
    public String handle(Field Filed);
}
