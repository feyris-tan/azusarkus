package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.azusa.entity.PlatformEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/azusa/platforms")
public class PlatformResource {

    @Inject
    RestLicenseService restLicenseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<PlatformEntity> getAll(@HeaderParam("Azusa-License") String lic)
    {
        restLicenseService.validateLicenseThrowing(lic);

        return PlatformEntity.findAll().list();
    }
}
