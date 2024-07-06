package ma.codex.Framework.ORM.Persistence.Annotations.Relations;

import ma.codex.Framework.ORM.Persistence.Enums.CascadeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ManyToOne {
    String name();

    String mappedBy();

    CascadeType cascade() default CascadeType.NONE;
}
