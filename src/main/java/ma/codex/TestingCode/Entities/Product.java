package ma.codex.TestingCode.Entities;

import ma.codex.Framework.Persistence.Annotations.Column;
import ma.codex.Framework.Persistence.Annotations.Entity;
import ma.codex.Framework.Persistence.Annotations.ID;
import ma.codex.Framework.Persistence.Annotations.Relations.ManyToOne;
import ma.codex.Framework.Persistence.Enums.CascadeType;

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
