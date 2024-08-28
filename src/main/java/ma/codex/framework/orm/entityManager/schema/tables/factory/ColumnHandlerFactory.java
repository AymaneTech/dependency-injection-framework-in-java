package ma.codex.framework.orm.entityManager.schema.tables.Factory;

import java.lang.reflect.Field;

import ma.codex.framework.orm.persistence.annotations.ID;
import ma.codex.framework.orm.entityManager.schema.tables.columnHandlers.ColumnHandler;
import ma.codex.framework.orm.entityManager.schema.tables.columnHandlers.ColumnHandlerImpl;
import ma.codex.framework.orm.entityManager.schema.tables.columnHandlers.PrimaryKeyHandlerImpl;

public class ColumnHandlerFactory {
    public static ColumnHandler get(Field field) {
        if (field.isAnnotationPresent(ID.class)) {
            return new PrimaryKeyHandlerImpl();
        }
        return new ColumnHandlerImpl();

    }
}
