package ma.codex.framework.orm.persistence.annotations.Relations;

import ma.codex.framework.orm.persistence.enums.CascadeType;

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
