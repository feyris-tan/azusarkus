package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.azusa.control.MediaRepository;
import moe.yo3explorer.azusa.azusa.entity.MediaEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/azusa/media")
public class MediaResource {
    @Inject
    RestLicenseService licenseService;

    @Inject
    MediaRepository mediaRepository;

    @GET
    @Path("/{byId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public MediaEntity findById(@HeaderParam("Azusa-License") String lic, @PathParam("byId") int id)
    {
        licenseService.validateLicenseThrowing(lic);

        return MediaEntity.findById(id);
    }

    @GET
    @Path("/inProduct/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<MediaEntity> findInProduct(@HeaderParam("Azusa-License") String lic, @PathParam("productId") int id)
    {
        licenseService.validateLicenseThrowing(lic);

        return MediaEntity.find("relatedproduct = ?1",id).list();
    }

    @GET
    @Path("/lists/full.json")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<MediaEntity> listAll(@HeaderParam("Azusa-License") String lic)
    {
        licenseService.validateLicenseThrowing(lic);

        return mediaRepository.listAll();
    }
}
