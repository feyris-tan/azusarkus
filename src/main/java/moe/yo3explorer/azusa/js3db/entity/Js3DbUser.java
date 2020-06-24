package moe.yo3explorer.azusa.js3db.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "users",schema = "js3db")
public class Js3DbUser extends PanacheEntityBase
{
    @Id
    public int id;
    @CreationTimestamp
    public Timestamp dateadded;
    public String username;
    public String salt;
    public String password;
    public String email;
}
