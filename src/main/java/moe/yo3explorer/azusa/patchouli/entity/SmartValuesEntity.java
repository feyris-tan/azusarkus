package moe.yo3explorer.azusa.patchouli.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "smart_values", schema = "patchouli", catalog = "postgres")
@IdClass(SmartValuesEntityPK.class)
public class SmartValuesEntity extends PanacheEntityBase {
    private Timestamp dateadded;
    private int driveid;
    private Integer _5;
    private Long _187;
    private Long _188;
    private Integer _197;
    private Integer _198;

    @Id
    @Column(name = "dateadded")
    public Timestamp getDateadded() {
        return dateadded;
    }

    public void setDateadded(Timestamp dateadded) {
        this.dateadded = dateadded;
    }

    @Id
    @Column(name = "driveid")
    public int getDriveid() {
        return driveid;
    }

    public void setDriveid(int driveid) {
        this.driveid = driveid;
    }

    @Basic
    @Column(name = "\"5\"")
    public Integer get_5() {
        return _5;
    }

    public void set_5(Integer _5) {
        this._5 = _5;
    }

    @Basic
    @Column(name = "\"187\"")
    public Long get_187() {
        return _187;
    }

    public void set_187(Long _187) {
        this._187 = _187;
    }

    @Basic
    @Column(name = "\"188\"")
    public Long get_188() {
        return _188;
    }

    public void set_188(Long _188) {
        this._188 = _188;
    }

    @Basic
    @Column(name = "\"197\"")
    public Integer get_197() {
        return _197;
    }

    public void set_197(Integer _197) {
        this._197 = _197;
    }

    @Basic
    @Column(name = "\"198\"")
    public Integer get_198() {
        return _198;
    }

    public void set_198(Integer _198) {
        this._197 = _198;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartValuesEntity that = (SmartValuesEntity) o;
        return driveid == that.driveid &&
                Objects.equals(dateadded, that.dateadded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateadded, driveid);
    }
}
