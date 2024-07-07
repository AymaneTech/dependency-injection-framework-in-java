package ma.codex.TestingCode.Entities;

import ma.codex.Framework.Persistence.Annotations.Column;
import ma.codex.Framework.Persistence.Annotations.Entity;
import ma.codex.Framework.Persistence.Annotations.ID;
import ma.codex.Framework.Persistence.Annotations.Relations.Definition;
import ma.codex.Framework.Persistence.Annotations.Relations.ManyToMany;
import ma.codex.Framework.Persistence.Annotations.Relations.ManyToOne;
import ma.codex.Framework.Persistence.Enums.CascadeType;

import java.util.List;

@Entity(name = "products")
public class Product {

    @ID
    @Column(name = "id")
    private Long id;

    @Column(name = "name", size = 40)
    private String name;

    @Column(name = "description", type = "text")
    private String description;

    @ManyToOne(mappedBy = "categories")
    @Definition(tableName = "products", columnName = "category_id", referencedTable = "categories", referencedColumn = "id", cascade = CascadeType.ALL)
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToMany
    private List<Cart> carts;
}
