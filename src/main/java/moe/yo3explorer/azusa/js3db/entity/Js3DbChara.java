package moe.yo3explorer.azusa.js3db.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "characters", schema = "js3db")
public class Js3DbChara extends PanacheEntityBase
{
    @Id
    public int id;
    public int type_id;
    public int set_id;
    public int user_id;
    public String name;
    public String link;
    public String crc32;
    public int size;
    public Timestamp time_added;
    public String description;
    public String tags;
    public int hits;
    public double rating;
    public boolean disabled;
}
