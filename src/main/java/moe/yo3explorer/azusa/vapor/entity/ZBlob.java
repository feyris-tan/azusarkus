package moe.yo3explorer.azusa.vapor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "zblob",schema = "vapor")
public class ZBlob extends PanacheEntityBase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String hashdeep;
    public byte[] blob;
    public Date dateadded;

    @Transient
    public boolean newlyCreated;
}
