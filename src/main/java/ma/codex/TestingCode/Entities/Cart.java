package ma.codex.TestingCode.Entities;

import ma.codex.Framework.Persistence.Annotations.Entity;
import ma.codex.Framework.Persistence.Annotations.ID;
import ma.codex.Framework.Persistence.Annotations.Relations.JoiningTable;
import ma.codex.Framework.Persistence.Annotations.Relations.ManyToMany;

import java.util.List;

@Entity(name = "carts")
public class Cart {
    @ID
    private Long id;

    @ManyToMany
    @JoiningTable(name = "cart_product", table1 = "carts", column1 = "cart_id", table2 = "products", column2 = "product_id")
    private List<Product> products;
}
