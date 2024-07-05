package ma.codex;

import ma.codex.Framework.DIContext.Injector;
import ma.codex.Framework.ORM.Database.DBConnection;
import ma.codex.Framework.ORM.TablesCreation.TableGenerator;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Injector DIContext = new Injector();
        DIContext.scanClasses(Application.class);

        new TableGenerator().scanEntities(Application.class);
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("enter your name: ");
//        String name = scanner.nextLine();
//        System.out.println("welcome to DI Framework!" + name);


    }
}
