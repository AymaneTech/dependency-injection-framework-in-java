package ma.codex.framework.orm.entityManager.schema.constraints;

import ma.codex.framework.orm.entityManager.schema.constraints.factory.ForeignKeyHandlerFactory;
import ma.codex.framework.orm.persistence.annotations.Relations.Definition;
import ma.codex.framework.orm.persistence.annotations.Relations.JoiningTable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstraintManager {
    private List<String> constraints;

    public List<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(final Collection<Class<?>> entityClasses) {
        constraints = entityClasses.stream()
                .map(this::checkFields)
                .collect(Collectors.toList());
    }

    private String checkFields(final Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .map(this::addConstraint)
                .filter(def -> !def.isEmpty())
                .collect(Collectors.joining(",\n"));
    }

    private String addConstraint(final Field field) {
        final String query;
        if (field.isAnnotationPresent(Definition.class)) {
            query = ForeignKeyHandlerFactory.get(field)
                    .handle(field.getAnnotation(Definition.class));
        } else if (field.isAnnotationPresent(JoiningTable.class)) {
            query = ForeignKeyHandlerFactory.get(field)
                    .handle(field.getAnnotation(JoiningTable.class));
        } else {
            query = "";
        }
        return query;
    }
}
