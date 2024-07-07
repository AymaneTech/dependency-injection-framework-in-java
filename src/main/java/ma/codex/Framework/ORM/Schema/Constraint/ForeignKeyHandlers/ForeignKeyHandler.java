package ma.codex.Framework.ORM.Schema.Constraint.ForeignKeyHandlers;

import ma.codex.Framework.Persistence.Annotations.Relations.Definition;

public sealed interface ForeignKeyHandler permits ManyToOneForeignKeyHandlerImpl, OneToOneForeignKeyHandlerImpl, ManyToManyForeignKeyHandlerImpl {
    public String handle(Definition definition);
}
