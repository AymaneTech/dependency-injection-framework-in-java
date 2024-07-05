package ma.codex.DIFramework.ClassesScanner;

import ma.codex.DIFramework.Annotations.Component;
import ma.codex.DIFramework.Annotations.Inject;
import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;

import java.util.Collection;

public class Scanner {

    public Collection<Class<?>> find(String packageName) {
        ComponentSupplier componentSupplier = ComponentContainer.getInstance();
        ClassHunter classHunter = componentSupplier.getClassHunter();

        try (ClassHunter.SearchResult result = classHunter.findBy(
                SearchConfig.forResources(
                        packageName
                ).by(
                        ClassCriteria.create().allThoseThatMatch((cls) -> {
                            return !cls.isAnnotation() && cls.isAnnotationPresent(Component.class);
                        })
                )
        )
        ) {
            return result.getClasses();
        }
    }

}