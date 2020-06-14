package moe.yo3explorer.azusa.skyscraper.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "services",schema = "skyscraper")
public class Service extends PanacheEntityBase {
    @Id
    public int id;
    public Timestamp dateadded;
    public int transponder;
    public int serviceid;
    public String name;
    public String runningstatus;
    public boolean fta;
    public String servicetype;
    public Timestamp lastseen;
}
