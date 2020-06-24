package moe.yo3explorer.azusa.js3db.boundary;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/js3db-revival")
public class Js3DbRevivalResource {

    @Inject
    Template js3dbrevivalIndex;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance indexHtml()
    {
        return js3dbrevivalIndex.instance();
    }
}
