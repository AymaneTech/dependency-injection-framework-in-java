package ma.codex.Framework.ORM.Schema.Tables.Factory;

import ma.codex.Framework.ORM.Schema.Tables.ColumnHandlers.ColumnHandler;
import ma.codex.Framework.ORM.Schema.Tables.ColumnHandlers.ColumnHandlerImpl;
import ma.codex.Framework.ORM.Schema.Tables.ColumnHandlers.PrimaryKeyHandlerImpl;
import ma.codex.Framework.Persistence.Annotations.ID;

import java.lang.reflect.Field;

public class ColumnHandlerFactory {
    public static ColumnHandler get(Field field) {
        if (field.isAnnotationPresent(ID.class)) {
            return new PrimaryKeyHandlerImpl();
        }
        return new ColumnHandlerImpl();

    }
}
