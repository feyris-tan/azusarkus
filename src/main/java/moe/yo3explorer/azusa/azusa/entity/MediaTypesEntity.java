package moe.yo3explorer.azusa.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "mediatypes",schema = "azusa")
public class MediaTypesEntity extends PanacheEntityBase {
    @Id
    public int id;
    public String shortName;
    public String longName;
    public boolean graphdata;
    public Timestamp dateadded;
    public byte[] icon;
    public boolean ignoreforstatistics;
    public String vndbkey;
    public boolean filesystem;
}
