package ma.codex.framework.ORM.ShcemaManager.Schema.Constraints.ForeignKeyHandlers;

import ma.codex.framework.ORM.Persistence.Annotations.Relations.Definition;
import ma.codex.framework.ORM.Persistence.Enums.CascadeType;

public final class ManyToOneForeignKeyHandlerImpl implements ForeignKeyHandler <Definition>{
    @Override
    public String handle(final Definition definition) {
        String query = String.format("ALTER TABLE %s ADD CONSTRAINT fk_%s_%s FOREIGN KEY (%s) REFERENCES %s(%s)",
                definition.tableName(), definition.tableName(), definition.columnName(), definition.columnName(), definition.referencedTable(), definition.referencedColumn());

        if (definition.cascade() != CascadeType.NONE) {
            query += definition.cascade().toSql();
        }
        return query;
    }
}
