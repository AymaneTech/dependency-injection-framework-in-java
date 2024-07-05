package ma.codex.DIFramework.DIFramework;

import ma.codex.DIFramework.Annotations.Autowired;
import ma.codex.DIFramework.Annotations.Component;
import ma.codex.DIFramework.Utils.Scanners.ScanByAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Injector {

    /*
     * Map to hold all components annotated with @Component
     * */
    private HashMap<String, Object> components = new HashMap<>();

    /*
     * scanClasses: Scans all classes in the specified package, checks for dependencies,
     * creates instances, and stores them in the components map.
     */
    public void scanClasses() {
        try {
            ScanByAnnotation scanner = new ScanByAnnotation(Component.class);
            Collection<Class<?>> result = scanner.find("ma/codex/DIFramework");

            result.stream().parallel().forEach((cls -> {
                Constructor<?>[] constructors = cls.getConstructors();
                Field[] fields = cls.getDeclaredFields();
                AtomicBoolean hasDependencies = new AtomicBoolean(false);

                Arrays.stream(constructors).parallel().forEach((constructor) -> {
                    if (constructor.isAnnotationPresent(Autowired.class)) {
                        inject(constructor.getDeclaringClass());
                        hasDependencies.set(true);
                    }
                });
                Arrays.stream(fields).parallel().forEach((field) -> {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        inject(field.getDeclaringClass());
                        hasDependencies.set(true);
                    }
                });
                if (!hasDependencies.get()) {
                    try {
                        Object instance = constructors[0].newInstance();
                        components.put(cls.getSimpleName(), instance);
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                        System.err.println("Error creating instance for class: " + cls.getName());
                        e.printStackTrace();
                    }
                }
            }));
            System.out.println(components.toString());
        } catch (RuntimeException e) {
            System.err.println("Error during class scanning and component creation.");
            e.printStackTrace();        }
    }

    /*
     * inject: Injects dependencies into the specified class.
     */
    private void inject(Class<?> declaringClass) {

    }

}
