package moe.yo3explorer.azusa.azusaDeprecated.sedgetree.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.azusa.control.ByteArrayBase64Adapter;
import moe.yo3explorer.azusa.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "versioning", schema = "sedgetree")
@Entity
public class VersionEntity extends PanacheEntityBase {
    @Id
    public int id;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateadded;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] data;
}
