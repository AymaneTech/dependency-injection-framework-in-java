package ma.codex.DIFramework.DIFramework;

import ma.codex.DIFramework.Annotations.Autowired;
import ma.codex.DIFramework.Annotations.Component;
import ma.codex.DIFramework.Utils.Scanners.ScanByAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;

/**
 * Injector class for Dependency Injection framework.
 * This class is responsible for scanning, instantiating, and injecting dependencies
 * for classes annotated with @Component.
 */
public class Injector {

    /**
     * Map to store all instantiated components.
     * Key: Fully qualified class name
     * Value: Instance of the component
     */
    private HashMap<String, Object> components = new HashMap<>();

    /**
     * Map to store all instantiated components.
     * Key: Fully qualified class name
     * Value: Instance of the component
     */
    public void scanClasses() {
        try {
            ScanByAnnotation scanner = new ScanByAnnotation(Component.class);
            Collection<Class<?>> result = scanner.find("ma/codex/DIFramework");

            result.forEach(this::instantiate);
            System.out.println(components.toString());
        } catch (RuntimeException e) {
            System.err.println("Error during class scanning and component creation.");
            e.printStackTrace();
        }
    }

    /**
     * Injects dependencies for a given class.
     * This method handles both constructor and field injection.
     *
     * @param declaringClass The class to inject dependencies for
     */
    private void inject(Class<?> declaringClass) {
        Constructor<?>[] constructors = declaringClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                injectConstructor(declaringClass, constructor);
                return;
            }
        }
        injectDefaultConstructor(declaringClass);
    }

    /**
     * Injects dependencies using the specified constructor.
     *
     * @param declaringClass The class to create an instance of
     * @param constructor    The constructor to use for instantiation
     */
    private void injectConstructor(Class<?> declaringClass, Constructor<?> constructor) {
        Parameter[] params = constructor.getParameters();
        Object[] paramInstances = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            Class<?> paramType = params[i].getType();
            paramInstances[i] = getOrCreateInstance(paramType);
        }

        try {
            Object instance = constructor.newInstance(paramInstances);
            components.put(declaringClass.getName(), instance);
            injectFields(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error creating instance for " + declaringClass.getName(), e);
        }
    }

    /**
     * Creates an instance using the default constructor and injects field dependencies.
     *
     * @param declaringClass The class to create an instance of
     */
    private void injectDefaultConstructor(Class<?> declaringClass) {
        try {
            Object instance = declaringClass.getDeclaredConstructor().newInstance();
            components.put(declaringClass.getName(), instance);
            injectFields(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Error creating instance for " + declaringClass.getName(), e);
        }
    }

    /**
     * Instantiates a class if it hasn't been instantiated already.
     *
     * @param clazz The class to instantiate
     */
    private void instantiate(Class<?> clazz) {
        if (!components.containsKey(clazz.getName())) {
            inject(clazz);
        }
    }

    /**
     * Retrieves an existing instance of a class or creates a new one if it doesn't exist.
     *
     * @param clazz The class to get or create an instance of
     * @return An instance of the specified class
     */
    private Object getOrCreateInstance(Class<?> clazz) {
        if (!components.containsKey(clazz.getName())) {
            instantiate(clazz);
        }
        return components.get(clazz.getName());
    }

    /**
     * Injects dependencies into fields annotated with @Autowired.
     *
     * @param instance The object to inject fields into
     */
    private void injectFields(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                try {
                    field.set(instance, getOrCreateInstance(field.getType()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error injecting field " + field.getName(), e);
                }
            }
        }
    }
}