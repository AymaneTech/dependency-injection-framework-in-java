package ma.codex.Framework.ORM.Schema.Constraint.ForeignKeyHandlers;

import ma.codex.Framework.Persistence.Annotations.Relations.Definition;
import ma.codex.Framework.Persistence.Annotations.Relations.JoiningTable;

public final class ManyToManyForeignKeyHandlerImpl implements ForeignKeyHandler <JoiningTable>{
    @Override
    public String handle(JoiningTable definition) {
        return String.format("""
                        CREATE TABLE IF NOT EXISTS %s (
                        %s INT,
                        %s INT,
                        PRIMARY KEY (%s, %s),
                        FOREIGN KEY (%s) REFERENCES %s(id),
                        FOREIGN KEY (%s) REFERENCES %s(id))
                        """, definition.name(), definition.column1(), definition.column2(), definition.column1(), definition.column2(),
                definition.column1(), definition.table2(),
                definition.column2(), definition.table1());
    }
}
