package ma.codex.TestingCode.Entities;

import ma.codex.Framework.ORM.Annotations.Column;
import ma.codex.Framework.ORM.Annotations.Entity;
import ma.codex.Framework.ORM.Annotations.ID;

import javax.management.NotificationEmitter;

@Entity(name = "entities3")
public class Entity3 {
    @ID
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", size = 50)
    private String username;

    @Column(name = "email", size = 50)
    private String email;
}
