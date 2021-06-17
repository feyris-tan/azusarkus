package moe.yo3explorer.azusa.boundary;

import moe.yo3explorer.azusa.control.MediaRepository;
import moe.yo3explorer.azusa.entity.MediaEntity;
import moe.yo3explorer.azusa.licensing.Licensed;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Licensed
@Path("/media")
public class MediaResource {

    @Inject
    MediaRepository mediaRepository;

    @GET
    @Path("/{byId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public MediaEntity findById(@PathParam("byId") int id)
    {
        return MediaEntity.findById(id);
    }

    @GET
    @Path("/inProduct/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<MediaEntity> findInProduct(@HeaderParam("Azusa-License") String lic, @PathParam("productId") int id)
    {
        return MediaEntity.find("relatedproduct = ?1",id).list();
    }

    @GET
    @Path("/lists/full.json")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<MediaEntity> listAll(@HeaderParam("Azusa-License") String lic)
    {
        return mediaRepository.listAll();
    }
}
