package moe.yo3explorer.azusa.patchouli.boundary;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.tuples.Tuple2;
import moe.yo3explorer.azusa.patchouli.entity.SmartDisk;
import moe.yo3explorer.azusa.patchouli.entity.SmartInfo;
import moe.yo3explorer.azusa.patchouli.entity.SmartValuesEntity;
import moe.yo3explorer.azusa.vapor.boundary.VaporRelated;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@VaporRelated
@Path("/patchouli")
public class SmartResource
{
    @Inject
    Template patchouliSmart;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/smart")
    public TemplateInstance smart()
    {
        List<SmartDisk> disksInUse = SmartDisk.find("decommissioned IS NULL").list();
        List<SmartInfo> collect = disksInUse.stream()
                .map(x -> Tuple2.of(x, (SmartValuesEntity) SmartValuesEntity.find("driveid = ?1 ORDER BY dateadded DESC", x.id).firstResult()))
                .map(x -> new SmartInfo(x))
                .collect(Collectors.toList());
        long health = 100;
        health -= collect.stream().mapToLong(SmartInfo::getHealthPenalty).sum();

        String healthColor = "red";
        if (health > 50)
            healthColor = "yellow";
        if (health > 75)
            healthColor = "green";

        return patchouliSmart.data("disks",collect).data("health",health).data("healthColor",healthColor);
    }
}
