package ma.codex.Framework.ORM.JPRepository;

import java.util.List;

public interface ListRepository <Entity, ID>{
    public List<Entity> findAll();

    public List<Entity> saveAll(List<Entity> entities);

    public List<Entity> findByIds(List<ID> ids);

    public boolean deleteByIds(List<ID> ids);

    public boolean deleteAll();
}
