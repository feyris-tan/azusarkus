package moe.yo3explorer.azusa.skyscraper.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "satellites", schema = "skyscraper")
@Entity
public class Satellite extends PanacheEntityBase
{
    @Id
    public int id;
    public Timestamp dateadded;
    public double orbitalposition;
    public String cardinaldirection;
    public String name;
    public Integer diseqc;
}
