package moe.yo3explorer.azusa.skyscraper.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "network_ids",schema = "skyscraper")
@Entity
public class NetworkId extends PanacheEntityBase {
    @Id
    public int id;
    public Timestamp dateadded;
    public String name;
    public String operator;
    public int countrycode;
    public String type;
}
