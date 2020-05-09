package moe.yo3explorer.azusa.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.azusa.control.ByteArrayBase64Adapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import java.util.Date;

@Table(name = "media",schema = "azusa")
@Entity
@NamedQueries({
    @NamedQuery(name = MediaEntity.FIND_KEYS_BY_PRODUCT,query = "SELECT new MediaEntity(a.id,a.relatedproduct,a.name,a.mediatypeid,a.dumppath,a.graphdata) FROM MediaEntity a WHERE a.relatedproduct = :pid")
})
public class MediaEntity extends PanacheEntityBase {
    public static final String FIND_KEYS_BY_PRODUCT = "Media.FindKeysByProduct";
    public MediaEntity() {}
    public MediaEntity(int id, int relatedproduct, String name, int mediatypeid, String dumppath, String graphdata) {
        this.id = id;
        this.relatedproduct = relatedproduct;
        this.name = name;
        this.mediatypeid = mediatypeid;
        this.dumppath = dumppath;
        this.graphdata = graphdata;
    }

    @Id
    public int id;
    public int relatedproduct;
    public String name;
    public int mediatypeid;
    public String sku;
    public Integer dumpstoragespace;
    public String dumppath;
    public String metafile;
    public Date dateadded;
    public String graphdata;
    public String untouchedcuesheet;
    public String untouchedchecksum;
    public String untouchedplaylist;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] cdtext;
    public String logfile;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] mediadescriptorsidecar;
    public boolean issealed;
    public Date dateupdated;
    public long fauxhash;
    public Long discid;
    public String cicm;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] mhddlog;
    public String scsiinfo;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] priv;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] jedecid;
}