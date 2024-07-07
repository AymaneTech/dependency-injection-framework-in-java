package ma.codex.Framework.ORM.Schema;

import ma.codex.Framework.ORM.Schema.Column.Factory.ColumnHandlerFactory;
import ma.codex.Framework.Persistence.Annotations.Entity;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SchemaGenerator {

    private List<String> schemas;

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(Collection<Class<?>> entityClasses) {
        schemas = entityClasses.stream()
                .map(this::generateSchema)
                .collect(Collectors.toList());
    }

    private String generateSchema(Class<?> entityClass) {
        String tableName = entityClass.getAnnotation(Entity.class).name();
        String columnDefinitions = getColumnDefinitions(entityClass);

        return String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                %s,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    deleted_at TIMESTAMP
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
                            
                CREATE OR REPLACE FUNCTION update_updated_at_column()
                RETURNS TRIGGER AS $$
                BEGIN
                    NEW.updated_at = CURRENT_TIMESTAMP;
                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;
                """, tableName, columnDefinitions, tableName.toLowerCase(), tableName.toLowerCase(), tableName);
    }

    private String getColumnDefinitions(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .map((Field field) -> {
                    return ColumnHandlerFactory.get(field).handle(field);
                })
                .filter(def -> !def.isEmpty())
                .collect(Collectors.joining(",\n"));
    }
}