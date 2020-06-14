package moe.yo3explorer.azusa.azusaDeprecated.warwalking.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "tours", schema = "warwalking")
public class TourEntity extends PanacheEntityBase
{
    @Id
    public int id;
    public long hash;
    @Column(name = "\"utimeRecordingStarted\"")
    public int utimeRecordingStarted;
    public String name;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateAdded;
}
