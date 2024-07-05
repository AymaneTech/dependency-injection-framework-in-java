package ma.codex.DIFramework;

import ma.codex.DIFramework.DIFramework.Injector;

import java.lang.reflect.InvocationTargetException;

public class Application {
    public static void main(String[] args) {
        Injector DIContext = new Injector();
        DIContext.startApplication(Application.class);
    }
}
