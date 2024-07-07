package ma.codex;

import ma.codex.Framework.Kernel;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Kernel.run(Application.class);
    }
}
