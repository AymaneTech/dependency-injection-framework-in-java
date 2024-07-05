package ma.codex.DIFramework;

import ma.codex.DIFramework.DIFramework.Injector;

import java.lang.reflect.InvocationTargetException;

public class Application {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        new Injector().scanClasses();
    }
}
