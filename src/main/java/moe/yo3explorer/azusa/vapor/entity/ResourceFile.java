package moe.yo3explorer.azusa.vapor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resources", schema = "vapor")
public class ResourceFile extends PanacheEntityBase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public int gameid;
    public long zblobid;
    public String filename;
    public short resourcetype;
    public Date dateadded;
    public boolean blobfirstseen;
}
