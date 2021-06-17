package moe.yo3explorer.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "countries",schema = "azusa")
@Entity
public class CountriesEntity extends PanacheEntityBase {
    @Id
    public int id;
    @Column(name = "\"displayName\"")
    public String displayName;
    @Column(name = "\"BTS\"")
    public String bts;
    public String currency;
    public double conversion;
    public int language;
    @Column(name = "\"singlePhaseVoltage\"")
    public Short singlePhaseVoltage;
    @Column(name = "\"voltageFrequency\"")
    public Short voltageFrequency;
    @Column(name = "\"dvdRegion\"")
    public Short dvdRegion;
    @Column(name = "\"blurayRegion\"")
    public Character blurayRegion;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp dateadded;
}
