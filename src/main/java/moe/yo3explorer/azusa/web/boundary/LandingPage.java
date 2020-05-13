package moe.yo3explorer.azusa.web.boundary;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import moe.yo3explorer.azusa.vapor.boundary.VaporRelated;
import moe.yo3explorer.azusa.vapor.entity.Game;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

@Path("/")
@Produces(MediaType.TEXT_HTML)
@VaporRelated
public class LandingPage
{
    @Inject
    Template dashboard;

    @GET
    public TemplateInstance get()
    {
        return dashboard.instance();
    }

    @Path("/dashboard")
    @GET
    public TemplateInstance getDashboard()
    {
        return dashboard.instance();
    }
}
