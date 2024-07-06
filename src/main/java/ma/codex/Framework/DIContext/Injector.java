package ma.codex.Framework.DIContext;

import ma.codex.Framework.DIContext.Annotations.Autowired;
import ma.codex.Framework.DIContext.Annotations.Component;
import ma.codex.Framework.DIContext.Annotations.Qualified;
import ma.codex.Framework.Utils.ScanByAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;

/**
 * Injector class for Dependency Injection framework.
 * This class is responsible for scanning, instantiating, and injecting
 * dependencies
 * for classes annotated with @Component.
 */
public class Injector {

    /**
     * Map to store all instantiated components.
     * Key: Fully qualified class name
     * Value: Instance of the component
     */
    private HashMap<String, Object> components = new HashMap<>();

    /*
     * Map to store interfaces with their implementations
     * 
     * @key: interface name
     * 
     * @value: implementation of this interface
     */
    private HashMap<String, Object> interfaces = new HashMap<>();

    /**
     * Map to store all instantiated components.
     * Key: Fully qualified class name
     * Value: Instance of the component
     */
    public void scanClasses(Class<?> mainClass) {
        String packageName = mainClass.getPackage().getName().replace(".", "/");

        try {
            ScanByAnnotation scanner = new ScanByAnnotation(Component.class);
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
    public Object getComponent(Class<?> componentClass) {
        return components.get(componentClass.getName());
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
            if (isInterface(params[i]))
                paramInstances[i] = getInterfaceImplementation(params[i]);
            else
                paramInstances[i] = getOrCreateInstance(params[i].getType());

        }

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
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                try {
                    if (field.isAnnotationPresent(Qualified.class))
                        field.set(instance, getInterfaceImplementation(field));
                    else
                        field.set(instance, getOrCreateInstance(field.getType()));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error injecting field " + field.getName(), e);
                }
            }
        }
    }

    private boolean isInterface(Parameter param) {
        return param.getType().isInterface() && param.isAnnotationPresent(Qualified.class);
    }

}