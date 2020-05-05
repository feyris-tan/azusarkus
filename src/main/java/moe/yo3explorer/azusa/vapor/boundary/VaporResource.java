package moe.yo3explorer.azusa.vapor.boundary;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/vapor")
@Tag(name = "vapor",description = "easyRPG Kollektionen für's Web!")
@VaporRelated
public class VaporResource {

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping()
    {
        return "Pong!";
    }
}
