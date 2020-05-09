package moe.yo3explorer.azusa.vapor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Table(name = "games",schema = "vapor")
@Entity
public class Game extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String vapor_sku;
    public String gametitle;
    public Byte mapeditmode;
    public Byte mapeditzoom;
    public Boolean fullpackageflag;
    public String knownversion;
    public Date dateadded;
    public long rpg_rtid;
    public byte[] lcfmaptree;
    public byte[] lcfdatabase;
    public Integer resourcedependency;
    public Date exedate;
    public Date ldbdate;
    public Date inidate;
    public Date lmtdate;
    public String contenthash;
    public boolean rtp;
    public int reshits;
    public int resmisses;
}
