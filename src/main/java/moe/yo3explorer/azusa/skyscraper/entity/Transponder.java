package moe.yo3explorer.azusa.skyscraper.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "transponders", schema = "skyscraper")
@Entity
public class Transponder extends PanacheEntityBase {
    @Id
    public int id;
    public Timestamp dateadded;
    public double frequency;
    public String polarization;
    public int symbolrate;
    public String fec;
    public String modulation;
    public boolean s2;
    public int satellite;
    public Integer network;
    public Integer transportstream;
    public Timestamp lastscanned;
    public Timestamp lastvalid;
    public boolean ignore;
    @Transient public List<Service> serviceList;
}
