package moe.yo3explorer.azusa.licensing;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "rest_licenses",schema = "web")
public class RestLicenseEntity extends PanacheEntityBase {
    @Id
    public int id;
    public String license;
    public String owner;
    public Timestamp dateadded;
    public boolean banned;
}
