package ma.codex.Framework.ORM.ShcemaManager.Schema.Tables.ColumnHandlers;

import java.lang.reflect.Field;

public sealed interface ColumnHandler permits ColumnHandlerImpl, PrimaryKeyHandlerImpl{
    public String handle(Field Filed);
}
