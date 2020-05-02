package moe.yo3explorer.azusa.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "shelves", schema = "azusa", catalog = "postgres")
public class ShelvesEntity extends PanacheEntityBase {
    private int id;
    private String name;
    private boolean showsku;
    private Boolean showregion;
    private Boolean showplatform;
    private boolean ignoreforstatistics;
    private boolean screenshotrequired;
    private Timestamp dateadded;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "showsku")
    public boolean isShowsku() {
        return showsku;
    }

    public void setShowsku(boolean showsku) {
        this.showsku = showsku;
    }

    @Basic
    @Column(name = "showregion")
    public Boolean getShowregion() {
        return showregion;
    }

    public void setShowregion(Boolean showregion) {
        this.showregion = showregion;
    }

    @Basic
    @Column(name = "showplatform")
    public Boolean getShowplatform() {
        return showplatform;
    }

    public void setShowplatform(Boolean showplatform) {
        this.showplatform = showplatform;
    }

    @Basic
    @Column(name = "ignoreforstatistics")
    public boolean isIgnoreforstatistics() {
        return ignoreforstatistics;
    }

    public void setIgnoreforstatistics(boolean ignoreforstatistics) {
        this.ignoreforstatistics = ignoreforstatistics;
    }

    @Basic
    @Column(name = "screenshotrequired")
    public boolean isScreenshotrequired() {
        return screenshotrequired;
    }

    public void setScreenshotrequired(boolean screenshotrequired) {
        this.screenshotrequired = screenshotrequired;
    }

    @Basic
    @Column(name = "dateadded")
    public Timestamp getDateadded() {
        return dateadded;
    }

    public void setDateadded(Timestamp dateadded) {
        this.dateadded = dateadded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelvesEntity that = (ShelvesEntity) o;
        return id == that.id &&
                showsku == that.showsku &&
                ignoreforstatistics == that.ignoreforstatistics &&
                screenshotrequired == that.screenshotrequired &&
                Objects.equals(name, that.name) &&
                Objects.equals(showregion, that.showregion) &&
                Objects.equals(showplatform, that.showplatform) &&
                Objects.equals(dateadded, that.dateadded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, showsku, showregion, showplatform, ignoreforstatistics, screenshotrequired, dateadded);
    }
}
