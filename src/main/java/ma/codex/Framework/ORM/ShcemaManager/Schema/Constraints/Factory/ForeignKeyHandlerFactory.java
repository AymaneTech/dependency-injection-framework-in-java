package ma.codex.Framework.ORM.ShcemaManager.Schema.Constraints.Factory;

import ma.codex.Framework.ORM.ShcemaManager.Schema.Constraints.ForeignKeyHandlers.ForeignKeyHandler;
import ma.codex.Framework.ORM.ShcemaManager.Schema.Constraints.ForeignKeyHandlers.ManyToManyForeignKeyHandlerImpl;
import ma.codex.Framework.ORM.ShcemaManager.Schema.Constraints.ForeignKeyHandlers.ManyToOneForeignKeyHandlerImpl;
import ma.codex.Framework.ORM.ShcemaManager.Schema.Constraints.ForeignKeyHandlers.OneToOneForeignKeyHandlerImpl;
import ma.codex.Framework.Persistence.Annotations.Relations.JoiningTable;
import ma.codex.Framework.Persistence.Annotations.Relations.ManyToOne;
import ma.codex.Framework.Persistence.Annotations.Relations.OneToOne;

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
