package ma.codex.DIFramework.ClassesScanner;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.*;

import java.util.Set;

public class Scanner {

    public Set<Class<?>> getPackageClasses(String packageName) {
        ComponentSupplier componentSupplier = ComponentContainer.getInstance();
        ClassHunter classHunter = componentSupplier.getClassHunter();

        SearchConfig searchConfig = SearchConfig.forResources(packageName)
                .by(ClassCriteria.create().allThoseThatHaveAMatchInHierarchy(cls -> cls.getAnnotations().length > 0));

        try (SearchResult result = classHunter.findBy(searchConfig)) {
            CriteriaWithClassElementsSupplyingSupport criteria = ClassCriteria.create();
            System.out.println(result.getClasses(criteria));
            return null;
        }
    }
}
