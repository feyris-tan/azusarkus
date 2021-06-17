package moe.yo3explorer.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "shops", schema = "azusa")
public class ShopsEntity extends PanacheEntityBase {
    @Id
    public int id;
    public String name;
    @Column(name = "\"isPeriodicEvent\"")
    public boolean isPeriodicEvent;
    public String url;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateadded;
}
