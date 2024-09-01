package ma.codex.framework.orm.entityManager.schema.constraints.foreignKeyHandlers;

import java.lang.annotation.Annotation;

public sealed interface ForeignKeyHandler <T extends Annotation> permits ManyToOneForeignKeyHandlerImpl, OneToOneForeignKeyHandlerImpl, ManyToManyForeignKeyHandlerImpl {
    public String handle(T definition);
}
