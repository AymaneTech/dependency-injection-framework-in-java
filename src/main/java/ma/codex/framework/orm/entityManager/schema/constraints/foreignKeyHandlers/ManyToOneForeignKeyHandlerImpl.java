package ma.codex.framework.orm.entityManager.schema.constraints.foreignKeyHandlers;

import ma.codex.framework.orm.persistence.annotations.Relations.Definition;
import ma.codex.framework.orm.persistence.enums.CascadeType;

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
