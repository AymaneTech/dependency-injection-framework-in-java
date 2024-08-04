package ma.codex.Framework.ORM.ShcemaManager.Schema.Tables.Factory;

import java.lang.reflect.Field;

import ma.codex.Framework.ORM.Persistence.Annotations.ID;
import ma.codex.Framework.ORM.ShcemaManager.Schema.Tables.ColumnHandlers.ColumnHandler;
import ma.codex.Framework.ORM.ShcemaManager.Schema.Tables.ColumnHandlers.ColumnHandlerImpl;
import ma.codex.Framework.ORM.ShcemaManager.Schema.Tables.ColumnHandlers.PrimaryKeyHandlerImpl;

public class ColumnHandlerFactory {
    public static ColumnHandler get(Field field) {
        if (field.isAnnotationPresent(ID.class)) {
            return new PrimaryKeyHandlerImpl();
        }
        return new ColumnHandlerImpl();

    }
}
