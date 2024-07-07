package ma.codex.Framework.Persistence.Annotations.Relations;

import ma.codex.Framework.Persistence.Enums.CascadeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Definition {
    String tableName();
    String columnName();
    String referencedTable();
    String referencedColumn();
    CascadeType cascade() default CascadeType.NONE;
}
