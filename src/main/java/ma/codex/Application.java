package ma.codex;

import ma.codex.Framework.Kernel;
import ma.codex.Framework.ORM.JPRepository.JPRepository;
import ma.codex.Framework.ORM.JPRepository.JPRepositoryImpl;
import ma.codex.Framework.ORM.ShcemaManager.Core.QueryExecutor;
import ma.codex.TestingCode.Entities.Category;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Kernel.run(Application.class);
        JPRepository repository = new JPRepositoryImpl<Category, Long>(new QueryExecutor());
        

    }
}
