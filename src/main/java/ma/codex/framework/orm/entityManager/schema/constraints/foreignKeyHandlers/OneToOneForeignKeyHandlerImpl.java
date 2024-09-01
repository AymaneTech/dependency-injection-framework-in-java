package ma.codex.framework.orm.entityManager.schema.constraints.foreignKeyHandlers;


import ma.codex.framework.orm.persistence.annotations.Relations.Definition;

public final class OneToOneForeignKeyHandlerImpl implements ForeignKeyHandler <Definition> {
    @Override
    public String handle(final Definition definition) {
        return String.format("ALTER TABLE %s ADD CONSTRAINT fk_%s_%s FOREIGN KEY (%s) REFERENCES %s(%s)",
                definition.tableName(), definition.tableName(), definition.columnName(), definition.columnName(), definition.referencedTable(), definition.referencedColumn());
    }
}
