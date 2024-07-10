package ma.codex.Framework.ORM.JPRepository;

import ma.codex.Framework.ORM.ShcemaManager.Core.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JPRepositoryImpl<Entity, ID> implements JPRepository<Entity, ID> {
    private Connection connection;

    public JPRepositoryImpl(DatabaseConnection connection) throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        
    }

    @Override
    public List<Entity> findAll() {
        return List.of();
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
}
