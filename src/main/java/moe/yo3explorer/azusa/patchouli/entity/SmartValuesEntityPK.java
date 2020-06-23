package moe.yo3explorer.azusa.patchouli.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class SmartValuesEntityPK implements Serializable {
    private Timestamp dateadded;
    private int driveid;

    @Column(name = "dateadded")
    @Id
    public Timestamp getDateadded() {
        return dateadded;
    }

    public void setDateadded(Timestamp dateadded) {
        this.dateadded = dateadded;
    }

    @Column(name = "driveid")
    @Id
    public int getDriveid() {
        return driveid;
    }

    public void setDriveid(int driveid) {
        this.driveid = driveid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartValuesEntityPK that = (SmartValuesEntityPK) o;
        return driveid == that.driveid &&
                Objects.equals(dateadded, that.dateadded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateadded, driveid);
    }
}
