package moe.yo3explorer.azusa.azusa.entity;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.azusa.control.DateUnixtimeAdapter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "platforms", schema = "azusa")
public class PlatformEntity extends PanacheEntityBase {
    @Id
    public int id;
    @Column(name = "\"shortName\"")
    public String shortName;
    @Column(name = "\"longName\"")
    public String longName;
    @Column(name = "\"isSoftware\"")
    public boolean isSoftware;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateadded;
}
