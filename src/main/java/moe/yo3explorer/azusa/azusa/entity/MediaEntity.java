package moe.yo3explorer.azusa.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.azusa.control.ByteArrayBase64Adapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "media",schema = "azusa")
@Entity
public class MediaEntity extends PanacheEntityBase {
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
