package ma.codex.Framework.ORM.JPRepository;

import ma.codex.Framework.ORM.ShcemaManager.Core.DatabaseConnection;
import ma.codex.Framework.ORM.ShcemaManager.Core.QueryExecutor;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

public class JPRepositoryImpl<Entity, ID> implements JPRepository<Entity, ID> {
    private final Class<Entity> type;
    private final QueryExecutor queryExecutor;

    /**
     * Unchecked means: the compiler tell you that you are doing something wrong, but you are sure that it is right.
     * compiler: i can't verify if this generic operation is type safe, so be careful
     */
    @SuppressWarnings("unchecked")
    public JPRepositoryImpl(DatabaseConnection connection, QueryExecutor queryExecutor) throws SQLException {
        this.queryExecutor = queryExecutor;
        this.type = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<Entity> findAll() {
        String query = String.format("SELECT * FROM %s", getTableName());
        queryExecutor.execute(query);

        return null;
    }

    @Override
    public Entity save(Entity o) {
        return o;
    }

    @Override
    public Entity findById(ID o) {
        return null;
    }

    @Override
    public boolean deleteById(ID o) {
        return false;
    }

    @Override
    public List<Entity> saveAll(List<Entity> entities) {
        return List.of();
    }

    @Override
    public List<Entity> findByIds(List<ID> ids) {
        return List.of();
    }

    @Override
    public boolean deleteByIds(List<ID> ids) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }


    private String getTableName() {
        if (!type.isAnnotationPresent(ma.codex.Framework.Persistence.Annotations.Entity.class))
            throw new IllegalArgumentException("this class is not annotated with @Entity");
        return type.getAnnotation(ma.codex.Framework.Persistence.Annotations.Entity.class).name();
    }
}
