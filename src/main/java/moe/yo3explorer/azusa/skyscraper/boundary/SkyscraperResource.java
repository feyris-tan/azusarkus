package moe.yo3explorer.azusa.skyscraper.boundary;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import moe.yo3explorer.azusa.skyscraper.control.SatelliteComparator;
import moe.yo3explorer.azusa.skyscraper.entity.Satellite;
import moe.yo3explorer.azusa.vapor.boundary.VaporRelated;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@VaporRelated
@Path("/skyscraper")
public class SkyscraperResource {
    @Inject
    Template skyscraperSatellites;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance indexHtml()
    {
        List<Satellite> satellites = Satellite.list("name != ?1", "???");
        satellites.sort(new SatelliteComparator());
        return skyscraperSatellites.data("sats",satellites);
    }
}
