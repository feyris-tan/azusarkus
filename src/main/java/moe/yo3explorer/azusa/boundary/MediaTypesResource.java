package moe.yo3explorer.azusa.boundary;

import moe.yo3explorer.azusa.entity.MediaTypesEntity;
import moe.yo3explorer.azusa.licensing.Licensed;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Licensed
@Path("/mediaTypes")
public class MediaTypesResource {

    @Inject
    Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<MediaTypesEntity> getAll()
    {
        return MediaTypesEntity.findAll().list();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    @Path("/{id}")
    public MediaTypesEntity findById(@PathParam("id") int id)
    {
        return MediaTypesEntity.findById(id);
    }
}
