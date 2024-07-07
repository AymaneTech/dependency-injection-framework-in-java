package ma.codex.Framework.ORM.Schema.Constraints.ForeignKeyHandlers;

import ma.codex.Framework.Persistence.Annotations.Relations.JoiningTable;

public final class ManyToManyForeignKeyHandlerImpl implements ForeignKeyHandler<JoiningTable> {
    @Override
    public String handle(JoiningTable definition) {
        return String.format("""
                CREATE OR REPLACE FUNCTION update_updated_at_column()
                RETURNS TRIGGER AS $$
                BEGIN
                    NEW.updated_at = CURRENT_TIMESTAMP;
                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;

                CREATE TABLE IF NOT EXISTS %s (
                    %s INT,
                    %s INT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    deleted_at TIMESTAMP,
                    PRIMARY KEY (%s, %s),
                    FOREIGN KEY (%s) REFERENCES %s(id),
                    FOREIGN KEY (%s) REFERENCES %s(id)
                );

                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'update_%s_updated_at') THEN
                        CREATE TRIGGER update_%s_updated_at
                        BEFORE UPDATE ON "%s"
                        FOR EACH ROW
                        EXECUTE FUNCTION update_updated_at_column();
                    END IF;
                END $$;
                """,
                definition.name(),
                definition.column1(), definition.column2(),
                definition.column1(), definition.column2(),
                definition.column1(), definition.table2(),
                definition.column2(), definition.table1(),
                definition.name().toLowerCase(), definition.name().toLowerCase(), definition.name());
    }
}