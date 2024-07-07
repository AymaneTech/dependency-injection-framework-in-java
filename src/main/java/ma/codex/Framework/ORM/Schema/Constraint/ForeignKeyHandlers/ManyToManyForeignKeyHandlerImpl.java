package ma.codex.Framework.ORM.Schema.Constraint.ForeignKeyHandlers;

import ma.codex.Framework.Persistence.Annotations.Relations.Definition;

public final class ManyToManyForeignKeyHandlerImpl implements ForeignKeyHandler {
    @Override
    public String handle(Definition definition) {
        return String.format("CREATE TABLE IF NOT EXIST %s (%s INT, %s INT, PRIMARY KEY (%s, %s), FOREIGN KEY (%s) REFERENCES %s(%s), FOREIGN KEY (%s) REFERENCES %s(%s))",
                definition.tableName(), definition.columnName(), definition.referencedColumn(), definition.columnName(), definition.referencedColumn(), definition.columnName(), definition.tableName(), definition.columnName(), definition.referencedColumn(), definition.referencedTable(), definition.referencedColumn());
    }
}
