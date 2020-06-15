package moe.yo3explorer.azusa.skyscraper.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "events",schema = "skyscraper")
@Entity
public class Event extends PanacheEntityBase {
    @Id
    public long id;
    public Timestamp dateadded;
    public int service;
    public Timestamp starttime;
    public Timestamp endtime;
    public String runningstatus;
    public int eventid;
    public boolean encrypted;
    public String title;
    public String subtitle;
    public String synopsis;
}
