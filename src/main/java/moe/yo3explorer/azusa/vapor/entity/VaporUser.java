package moe.yo3explorer.azusa.vapor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "users", schema = "vapor")
public class VaporUser extends PanacheEntity {
    public String username;
    public String password;
    public Date dateadded;
}
