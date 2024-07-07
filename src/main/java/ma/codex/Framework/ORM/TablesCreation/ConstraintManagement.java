package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Framework.ORM.Persistence.Annotations.Column;
import ma.codex.Framework.ORM.Persistence.Annotations.Entity;
import ma.codex.Framework.ORM.Persistence.Annotations.Relations.ManyToOne;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstraintManagement {
    private List<String> constraints;


    public List<String> getConstraints() {
        return constraints;
    }

    public void generateSchema(Collection<Class<?>> entityClasses) {
        constraints = entityClasses.stream()
                .map(this::generateQuery)
                .collect(Collectors.toList());
    }

    private String generateQuery(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .map(this::addConstraint)
                .filter(def -> !def.isEmpty())
                .collect(Collectors.joining(",\n"));
    }

    public String addConstraint(Field field) {

        if (field.isAnnotationPresent(ManyToOne.class)) {
            String tableName = field.getDeclaringClass().getAnnotation(Entity.class).name();
            String keyName = field.getAnnotation(Column.class).name();
            String mappedBy = field.getAnnotation(ManyToOne.class).mappedBy();
            String primaryKey = field.getAnnotation(ManyToOne.class).name();

            return String.format("ALTER TABLE %s ADD CONSTRAINT fk_%s_%s FOREIGN KEY (%s) REFERENCES %s(%s)",
                    tableName, tableName, keyName, keyName, mappedBy, primaryKey);
        }
        return "";
    }


}

// field.isAnnotationPresent(OneToOne.class)
//                        && !field.getAnnotation(OneToOne.class).name().isEmpty()