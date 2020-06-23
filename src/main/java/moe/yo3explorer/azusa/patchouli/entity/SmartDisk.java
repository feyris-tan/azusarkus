package moe.yo3explorer.azusa.patchouli.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "smart_disks",schema = "patchouli")
public class SmartDisk extends PanacheEntityBase
{
    @Id
    public int id;
    public String model;
    public String serialno;
    public String wwn;
    public String firmware;
    public Timestamp dateadded;
    public int firstmachine;
    public Date decommissioned;
    public BigInteger capacity;
}
