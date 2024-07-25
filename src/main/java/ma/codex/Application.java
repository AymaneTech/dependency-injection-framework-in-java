package ma.codex;

import java.sql.SQLException;

import ma.codex.Framework.Kernel;

public class Application {
    public static void main(String[] args) throws SQLException {
        Kernel.run(Application.class);
        System.out.println("here is the world");
        
    }
}




