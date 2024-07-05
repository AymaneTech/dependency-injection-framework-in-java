package ma.codex;

import ma.codex.Framework.DIContext.Injector;
import ma.codex.Framework.ORM.Database.DBConnection;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Injector DIContext = new Injector();
        DIContext.startApplication(Application.class);

//        Scanner scanner = new Scanner(System.in);
//        System.out.print("enter your name: ");
//        String name = scanner.nextLine();
//        System.out.println("welcome to DI Framework!" + name);

        DBConnection connection = DBConnection.getInstance();

    }
}
