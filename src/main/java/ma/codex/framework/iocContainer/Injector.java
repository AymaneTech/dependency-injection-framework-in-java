package ma.codex.framework.iocContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import ma.codex.framework.iocContainer.annotations.Autowired;
import ma.codex.framework.iocContainer.annotations.Component;
import ma.codex.framework.iocContainer.annotations.Qualified;
import ma.codex.framework.utils.ScanByAnnotation;

/**
 * Injector class for Dependency Injection framework.
 * This class is responsible for scanning, instantiating, and injecting
 * dependencies
 * for classes annotated with @Component.
 */
public class Injector {

    private final ScanByAnnotation scanner;

    /**
     * Map to store all instantiated components.
     * Key: Fully qualified class name
     * Value: Instance of the component
     */
    private final HashMap<String, Object> components = new HashMap<>();

    /**
     * Map to store interfaces with their implementations
     *
     * @key: interface name
     * @value: implementation of this interface
     */
    private final HashMap<String, Object> interfaces = new HashMap<>();

    private final String packageName;

    public Injector(ScanByAnnotation scanner, Class<?> mainClass) {
        this.scanner = scanner;
        scanner.setAnnotation(Component.class);
        packageName = setPackageName(mainClass);
    }

    /**
     * Map to store all instantiated components.
     * Key: Fully qualified class name
     * Value: Instance of the component
     */
    public void run() {
        try {
            Collection<Class<?>> result = scanner.find(packageName);
            result.forEach(this::instantiate);
        } catch (RuntimeException e) {
            System.err.println("Error during class scanning and component creation.");
            e.printStackTrace();
        }
    }

    /**
     * a getter to retrieve a component from the DI Context
     *
     * @param componentClass The class to inject dependencies for
     * @return an instance of the specified class
     */
    public <T> T getComponent(Class<?> componentClass) {
        return (T) components.get(componentClass.getName());
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
        Object[] paramInstances = Arrays.stream(constructor.getParameters())
                .filter(this::isInterface)
                .map(param -> {
                    return isInterface(param)
                            ? getInterfaceImplementation(param)
                            : getOrCreateInstance(param.getType());
                })
                .toArray();

        try {
            Object instance = constructor.newInstance(paramInstances);
            components.put(declaringClass.getName(), instance);
            injectFields(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error creating instance for " + declaringClass.getName(), e);
        }
    }

    private Object getInterfaceImplementation(Field field) {
        if (!interfaces.containsKey(field.getType().getName()))
            bindInterface(field.getType().getName(), field.getAnnotation(Qualified.class).value());

        return interfaces.get(field.getType().getName());
    }

    private Object getInterfaceImplementation(Parameter param) {
        if (!interfaces.containsKey(param.getType().getName()))
            bindInterface(param.getType().getName(), param.getAnnotation(Qualified.class).value());

        return interfaces.get(param.getType().getName());
    }

    private void bindInterface(String key, Class<?> value) {
        if (!components.containsKey(value.getName()))
            instantiate(value);

        interfaces.put(key, components.get(value.getName()));
    }

    /**
     * Creates an instance using the default constructor and injects field
     * dependencies.
     *
     * @param declaringClass The class to create an instance of
     */
    private void injectDefaultConstructor(Class<?> declaringClass) {
        try {
            Object instance = declaringClass.getDeclaredConstructor().newInstance();
            components.put(declaringClass.getName(), instance);
            injectFields(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            throw new RuntimeException("Error creating instance for " + declaringClass.getName(), e);
        }
    }

    /**
     * Instantiates a class if it hasn't been instantiated already.
     *
     * @param clazz The class to instantiate
     */
    private void instantiate(Class<?> clazz) {
        if (!components.containsKey(clazz.getName()))
            inject(clazz);
    }

    /**
     * Retrieves an existing instance of a class or creates a new one if it doesn't
     * exist.
     *
     * @param clazz The class to get or create an instance of
     * @return An instance of the specified class
     */
    private Object getOrCreateInstance(Class<?> clazz) {
        if (!components.containsKey(clazz.getName()))
            instantiate(clazz);

        return components.get(clazz.getName());
    }

    /**
     * Injects dependencies into fields annotated with @Autowired.
     *
     * @param instance The object to inject fields into
     */
    private void injectFields(Object instance) {
        Arrays
            .stream(instance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .forEach(field -> injectField(instance, field));

    }

    private void injectField(Object instance, Field field) {
        field.setAccessible(true);

        try {
            var value = isInterface(field)
                    ? getInterfaceImplementation(field)
                    : getOrCreateInstance(field.getType());
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error injecting field " + field.getName(), e);
        }

    }

    private boolean isInterface(Parameter param) {
        return param.getType().isInterface() && param.isAnnotationPresent(Qualified.class);
    }

    private boolean isInterface(Field field) {
        return field.getType().isInterface() && field.isAnnotationPresent(Qualified.class);
    }

    private String setPackageName(Class<?> mainClass) {
        return mainClass.getPackageName().replace(".", "/");
    }
}
