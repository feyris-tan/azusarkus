package moe.yo3explorer.azusa.web.boundary;

import moe.yo3explorer.azusa.web.entity.PingResponse;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/ping")
public class PingResource {
    @Inject
    Random random;

    @Tag(name = "web")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PingResponse get()
    {
        PingResponse pingResponse = new PingResponse();
        pingResponse.setRandomNumber(random.nextInt());
        return pingResponse;
    }
}
