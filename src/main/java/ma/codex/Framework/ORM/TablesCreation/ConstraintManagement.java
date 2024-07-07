package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Framework.ORM.Persistence.Annotations.Column;
import ma.codex.Framework.ORM.Persistence.Annotations.Entity;
import ma.codex.Framework.ORM.Persistence.Annotations.Relations.ManyToOne;
import ma.codex.Framework.ORM.Persistence.Annotations.Relations.OneToOne;

import java.lang.reflect.Field;
import java.util.List;

public class ConstraintManagement {
    private List<String> constraints;


    public List<String> getConstraints() {
        return constraints;
    }

    public void addConstraint(Field field) {
        String tableName = field.getDeclaringClass().getAnnotation(Entity.class).name();
        String keyName = field.getAnnotation(Column.class).name();

        if (field.isAnnotationPresent(ManyToOne.class) ||
                field.isAnnotationPresent(OneToOne.class)
                        && !field.getAnnotation(OneToOne.class).name().isEmpty()) {

            String mappedBy = field.getAnnotation(ManyToOne.class).mappedBy();
            String primaryKey = field.getAnnotation(ManyToOne.class).name();

            constraints.add(
                    String.format("ALTER TABLE %s ADD CONSTRAINT fk_%s_%s FOREIGN KEY (%s) REFERENCES %s(%s)",
                            tableName, tableName, keyName, keyName, primaryKey, mappedBy)
            );
        }
    }


}
