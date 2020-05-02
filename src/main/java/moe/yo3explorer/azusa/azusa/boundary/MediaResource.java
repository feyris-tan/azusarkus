package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.azusa.entity.MediaEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/azusa/media")
public class MediaResource {
    @Inject
    RestLicenseService licenseService;

    @GET
    @Path("/{byId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public MediaEntity findById(@HeaderParam("Azusa-License") String lic, @PathParam("byId") int id)
    {
        licenseService.validateLicenseThrowing(lic);

        return MediaEntity.findById(id);
    }
}
