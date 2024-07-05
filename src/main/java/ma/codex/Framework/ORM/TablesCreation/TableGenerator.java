package ma.codex.Framework.ORM.TablesCreation;

import ma.codex.Framework.ORM.Annotations.Entity;
import ma.codex.Framework.Utils.ScanByAnnotation;

import java.util.Collection;

public class TableGenerator {

    public void scanEntities(Class<?> mainClass) {
        String packageName = mainClass.getPackage().getName().replace(".", "/");

        ScanByAnnotation scanner = new ScanByAnnotation(Entity.class);
        Collection<?> result = scanner.find(packageName);
        System.out.println("here are the results: ");
        System.out.println(result);
    }
}
