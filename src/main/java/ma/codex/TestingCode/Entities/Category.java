package ma.codex.TestingCode.Entities;

import ma.codex.Framework.Persistence.Annotations.Column;
import ma.codex.Framework.Persistence.Annotations.Entity;
import ma.codex.Framework.Persistence.Annotations.ID;
import ma.codex.Framework.Persistence.Annotations.Relations.OneToMany;

import java.util.List;

@Entity(name = "categories")
public class Category {

    @ID
    private Long id;

    @Column(name = "name", size = 40)
    private String name;

    @Column(name = "description", type = "text")
    private String description;

    @OneToMany(mappedBy = "products")
    private List<Product> products;
}
