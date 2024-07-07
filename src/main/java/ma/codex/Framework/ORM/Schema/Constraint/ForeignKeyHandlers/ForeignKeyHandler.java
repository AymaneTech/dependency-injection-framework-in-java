package ma.codex.Framework.ORM.Schema.Constraint.ForeignKeyHandlers;

import ma.codex.Framework.Persistence.Annotations.Relations.Definition;
import ma.codex.Framework.Persistence.Annotations.Relations.JoiningTable;

import java.lang.annotation.Annotation;

public sealed interface ForeignKeyHandler <T extends Annotation> permits ManyToOneForeignKeyHandlerImpl, OneToOneForeignKeyHandlerImpl, ManyToManyForeignKeyHandlerImpl {
    public String handle(T definition);
}
