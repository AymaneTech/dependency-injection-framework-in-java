package ma.codex.TestingCode.Entities;

import ma.codex.Framework.ORM.Annotations.Entity;
import ma.codex.Framework.ORM.Annotations.ID;

@Entity(name = "entities")
public class Entity1 {
    @ID
    private Integer id;
    private String name;
}
