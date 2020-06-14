package moe.yo3explorer.azusa.azusaDeprecated.notebook.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "notes",schema = "notebook")
public class NoteEntity extends PanacheEntityBase {
    @Id
    public int id;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateadded;
    public boolean iscategory;
    public String richtext;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateupdated;
    public Integer parent;
    public String name;
}
