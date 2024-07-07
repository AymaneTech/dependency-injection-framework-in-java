package ma.codex.Framework.ORM.Schema.Column.ColumnHandlers;

import ma.codex.Framework.Persistence.Annotations.Column;

import java.io.FileDescriptor;
import java.lang.reflect.Field;

public sealed interface ColumnHandler permits ColumnHandlerImpl, PrimaryKeyHandlerImpl{
    public String handle(Field Filed);
}
