package ma.codex;

import ma.codex.Framework.DIContext.Injector;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Injector DIContext = new Injector();
        DIContext.startApplication(Application.class);

        Scanner scanner = new Scanner(System.in);
        System.out.print("enter your name: ");
        String name = scanner.nextLine();
        System.out.println("welcome to DI Framework!" + name);
    }
}
