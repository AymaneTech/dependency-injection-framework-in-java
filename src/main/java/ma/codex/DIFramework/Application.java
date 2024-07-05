package ma.codex.DIFramework;

import ma.codex.DIFramework.Annotations.Component;
import ma.codex.DIFramework.Annotations.Inject;
import ma.codex.DIFramework.ClassesScanner.Scanner;

import javax.sound.midi.Soundbank;
import java.util.Collection;
@Component
@Inject
public class Application {
    public static void main(String[] args) {
        Collection<Class<?>> result = new Scanner().find("ma/codex/DIFramework");
        System.out.println("____________________________________________________________________");
        System.out.println(result);
        System.out.println("____________________________________________________________________");
    }
}
