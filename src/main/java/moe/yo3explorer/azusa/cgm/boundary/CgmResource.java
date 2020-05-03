package moe.yo3explorer.azusa.cgm.boundary;

import moe.yo3explorer.azusa.cgm.control.HistoryService;
import moe.yo3explorer.azusa.cgm.entity.HistoryEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/cgm")
public class CgmResource {
    @Inject
    HistoryService historyService;

    @Inject
    RestLicenseService licenseService;

    @Inject
    Logger logger;

    @GET
    @Path("/dates")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "cgm")
    public JsonArray findDates(@HeaderParam("Azusa-License") String lic)
    {
        licenseService.validateLicenseThrowing(lic);

        List<Date> dates = historyService.getDates();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        dates.stream().mapToLong(x -> x.getTime()).forEach(arrayBuilder::add);
        return arrayBuilder.build();
    }

    @GET
    @Path("/timeline/{day}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "cgm")
    public List<HistoryEntity> findTimeline(@HeaderParam("Azusa-License") String lic, @PathParam("day") long unixtime)
    {
        Date date = new Date(unixtime * 1000);
        logger.infof("Looking at date %s",date.toString());
        return historyService.findByDay(date);
    }

}
