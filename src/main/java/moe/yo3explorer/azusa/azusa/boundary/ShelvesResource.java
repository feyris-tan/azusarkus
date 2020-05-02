package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.azusa.entity.ShelvesEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/azusa/shelves")
public class ShelvesResource
{
    @Inject
    RestLicenseService licenseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<ShelvesEntity> getAll(@HeaderParam("Azusa-License") String license)
    {
        licenseService.validateLicenseThrowing(license);
        List<ShelvesEntity> shelves = ShelvesEntity.listAll();
        return shelves;
    }
}
