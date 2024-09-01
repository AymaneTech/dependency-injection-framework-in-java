package ma.codex.framework.orm.entityManager.schema.constraints.factory;

import ma.codex.framework.orm.entityManager.schema.constraints.foreignKeyHandlers.ForeignKeyHandler;
import ma.codex.framework.orm.entityManager.schema.constraints.foreignKeyHandlers.ManyToManyForeignKeyHandlerImpl;
import ma.codex.framework.orm.entityManager.schema.constraints.foreignKeyHandlers.ManyToOneForeignKeyHandlerImpl;
import ma.codex.framework.orm.entityManager.schema.constraints.foreignKeyHandlers.OneToOneForeignKeyHandlerImpl;
import ma.codex.framework.orm.persistence.annotations.Relations.JoiningTable;
import ma.codex.framework.orm.persistence.annotations.Relations.ManyToOne;
import ma.codex.framework.orm.persistence.annotations.Relations.OneToOne;

import java.lang.reflect.Field;

public class ForeignKeyHandlerFactory {
    public static ForeignKeyHandler get(Field field) {
        if (field.isAnnotationPresent(ManyToOne.class)) {
            return new ManyToOneForeignKeyHandlerImpl();
        } else if (field.isAnnotationPresent(OneToOne.class) && !field.getAnnotation(OneToOne.class).name().isEmpty()) {
            return new OneToOneForeignKeyHandlerImpl();
        } else if (field.isAnnotationPresent(JoiningTable.class)) {
            return new ManyToManyForeignKeyHandlerImpl();
        } else {
            throw new RuntimeException("No handler found for the given field");
        }
    }
}
