package ma.codex.Framework.ORM.ShcemaManager.Schema.Constraints.ForeignKeyHandlers;

import ma.codex.Framework.Persistence.Annotations.Relations.Definition;
import ma.codex.Framework.Persistence.Enums.CascadeType;

public final class ManyToOneForeignKeyHandlerImpl implements ForeignKeyHandler <Definition>{
    @Override
    public String handle(Definition definition) {
        String format = String.format("ALTER TABLE %s ADD CONSTRAINT fk_%s_%s FOREIGN KEY (%s) REFERENCES %s(%s)",
                definition.tableName(), definition.tableName(), definition.columnName(), definition.columnName(), definition.referencedTable(), definition.referencedColumn());

        if (definition.cascade() != CascadeType.NONE) {
            format += definition.cascade().toSql();
        }
        return format;
    }
}
