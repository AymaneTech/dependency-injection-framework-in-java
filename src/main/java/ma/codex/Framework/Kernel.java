package ma.codex.Framework;

import ma.codex.Framework.DIContext.Injector;
import ma.codex.Framework.ORM.ShcemaManager.Core.ORMKernel;
import ma.codex.Framework.Utils.ScanByAnnotation;

public class Kernel {

    public static void run(Class<?> mainClass) {
        Injector DIContext = new Injector(new ScanByAnnotation(), mainClass);
        DIContext.run();

        ORMKernel kernel = DIContext.getComponent(ORMKernel.class);
        kernel.setPackageName(mainClass);
        kernel.run();
    }
}
