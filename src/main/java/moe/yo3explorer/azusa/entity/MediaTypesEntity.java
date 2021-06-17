package moe.yo3explorer.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.control.ByteArrayBase64Adapter;
import moe.yo3explorer.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "mediatypes",schema = "azusa")
public class MediaTypesEntity extends PanacheEntityBase {
    @Id
    public int id;
    public String shortName;
    public String longName;
    public boolean graphdata;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateadded;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] icon;
    public boolean ignoreforstatistics;
    public String vndbkey;
    public boolean filesystem;
}
