package moe.yo3explorer.azusa.skyscraper.boundary;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import moe.yo3explorer.azusa.skyscraper.control.SatelliteComparator;
import moe.yo3explorer.azusa.skyscraper.entity.*;
import moe.yo3explorer.azusa.vapor.boundary.VaporRelated;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@VaporRelated
@Path("/skyscraper")
public class SkyscraperResource {
    @Inject
    Template skyscraperSatellites;

    @Inject
    Template skyscraperTransponders;

    @Inject
    Template skyscraperService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Tag(name = "webinterface")
    public TemplateInstance indexHtml()
    {
        List<Satellite> satellites = Satellite.list("name != ?1", "???");
        satellites.sort(new SatelliteComparator());
        return skyscraperSatellites.data("sats",satellites);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/sat{id}.dat")
    @Tag(name = "webinterface")
    public TemplateInstance listTransponders(@PathParam("id") int id)
    {
        Satellite satellite = Satellite.findById(id);
        List<Transponder> transponders = Transponder.list("satellite = ?1",id);
        transponders.sort(Comparator.comparingDouble(x -> x.frequency));
        return skyscraperTransponders
                .data("transponders",transponders)
                .data("sat",satellite);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/service{id}.dat")
    @Tag(name = "webinterface")
    public TemplateInstance listEvents(@PathParam("id") int id)
    {
        Service service = Service.findById(id);
        return skyscraperService.data("service",service);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/events{id}.json")
    @Tag(name = "skyscraper")
    public List<CalendarEvent> getEvents(@PathParam("id") int id)
    {
        List<Event> list = Event.list("service = ?1", id);
        return list.stream().map(CalendarEvent::new).collect(Collectors.toList());
    }
}
