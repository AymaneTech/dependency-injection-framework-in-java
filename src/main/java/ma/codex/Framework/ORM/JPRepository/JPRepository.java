package ma.codex.Framework.ORM.JPRepository;

public interface JPRepository <Entity, ID> extends ListRepository<Entity, ID>{
    Entity save(Entity entity);

    Entity findById(ID id);

    boolean deleteById(ID id);
}
