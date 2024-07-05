package ma.codex.DIFramework;

import ma.codex.DIFramework.ClassesScanner.Scanner;

public class Application {
    public static void main(String[] args) {
        new Scanner().getPackageClasses("ma.codex.DIFramework");
    }
}
