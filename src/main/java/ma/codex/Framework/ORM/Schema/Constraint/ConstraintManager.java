package ma.codex.Framework.ORM.Schema.Constraint;

import ma.codex.Framework.ORM.Schema.Constraint.Factory.ForeignKeyHandlerFactory;
import ma.codex.Framework.Persistence.Annotations.Relations.Definition;

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

    public void setConstraints(Collection<Class<?>> entityClasses) {
        constraints = entityClasses.stream()
                .map(this::checkFields)
                .collect(Collectors.toList());
    }

    private String checkFields(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .map(this::addConstraint)
                .filter(def -> !def.isEmpty())
                .collect(Collectors.joining(",\n"));
    }

    private String addConstraint(Field field) {
        if (!field.isAnnotationPresent(Definition.class))
            return "";
        return ForeignKeyHandlerFactory.get(field)
                .handle(field.getAnnotation(Definition.class));
    }


}
