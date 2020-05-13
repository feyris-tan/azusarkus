package moe.yo3explorer.azusa.vapor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Table(name = "games",schema = "vapor")
@Entity
public class Game extends PanacheEntityBase {

    public Game()
    {
    }

    public Game(int id, String vapor_sku, String gametitle, Date dateadded, long rpg_rtid, Date lmtdate, int reshits, int resmisses) {
        this.id = id;
        this.vapor_sku = vapor_sku;
        this.gametitle = gametitle;
        this.dateadded = dateadded;
        this.rpg_rtid = rpg_rtid;
        this.lmtdate = lmtdate;
        this.reshits = reshits;
        this.resmisses = resmisses;
        this.restotal = reshits + resmisses;
    }

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
    public byte[] exfont;

    @Transient
    public int restotal;
}
