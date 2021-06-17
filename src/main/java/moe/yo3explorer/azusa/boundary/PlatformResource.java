package moe.yo3explorer.azusa.boundary;

import moe.yo3explorer.azusa.entity.PlatformEntity;
import moe.yo3explorer.azusa.licensing.Licensed;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Licensed
@Path("/platforms")
public class PlatformResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<PlatformEntity> getAll()
    {
        return PlatformEntity.findAll().list();
    }
}
