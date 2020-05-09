package moe.yo3explorer.azusa.vapor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "maps",schema = "vapor")
public class MapFile extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public int gameid;
    public short mapnumber;
    public Date dateadded;
    public byte[] mapdata;
    public Date filedate;
}
