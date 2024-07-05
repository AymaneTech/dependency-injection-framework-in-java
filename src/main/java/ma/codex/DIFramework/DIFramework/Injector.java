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

public class Injector {
    private HashMap<String, Object> components = new HashMap<>();

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

    private void inject(Class<?> declaringClass) {
        Constructor<?>[] constructors = declaringClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
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
                return;
            }
        }

        // If no @Autowired constructor, use the default constructor
        try {
            Object instance = declaringClass.getDeclaredConstructor().newInstance();
            components.put(declaringClass.getName(), instance);
            injectFields(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Error creating instance for " + declaringClass.getName(), e);
        }
    }

    private void instantiate(Class<?> clazz) {
        if (!components.containsKey(clazz.getName())) {
            inject(clazz);
        }
    }

    private Object getOrCreateInstance(Class<?> clazz) {
        if (!components.containsKey(clazz.getName())) {
            instantiate(clazz);
        }
        return components.get(clazz.getName());
    }

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