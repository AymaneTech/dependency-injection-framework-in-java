package ma.codex.TestingCode.Entities;

import ma.codex.Framework.ORM.Persistence.Annotations.Column;
import ma.codex.Framework.ORM.Persistence.Annotations.Entity;
import ma.codex.Framework.ORM.Persistence.Annotations.ID;
import ma.codex.Framework.ORM.Persistence.Annotations.Relations.ManyToOne;
import ma.codex.Framework.ORM.Persistence.Enums.CascadeType;

@Entity(name = "products")
public class Product {

    @ID
    private Long id;

    @Column(name = "name", size = 40)
    private String name;

    @Column(name = "description", type = "text")
    private String description;

    @ManyToOne(mappedBy = "categories", name = "id", cascade = CascadeType.ALL)
    @Column(name = "category_id")
    private Long categoryId;
}
