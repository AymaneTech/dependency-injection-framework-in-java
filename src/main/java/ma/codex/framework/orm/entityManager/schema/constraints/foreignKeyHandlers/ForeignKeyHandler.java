package ma.codex.framework.ORM.ShcemaManager.Schema.Constraints.ForeignKeyHandlers;

import java.lang.annotation.Annotation;

public sealed interface ForeignKeyHandler <T extends Annotation> permits ManyToOneForeignKeyHandlerImpl, OneToOneForeignKeyHandlerImpl, ManyToManyForeignKeyHandlerImpl {
    public String handle(T definition);
}
