package ma.codex.framework.orm.persistence.annotations.Relations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoiningTable {
    String name();

    String table1();
    String column1();
    String table2();
    String column2();
}
