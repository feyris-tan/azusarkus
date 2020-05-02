package moe.yo3explorer.azusa.warwalking.boundary;

import moe.yo3explorer.azusa.warwalking.entity.TourEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/warwalking/")
public class WarwalkingResource
{
    @Inject
    RestLicenseService licenseService;

    @GET
    @Path("tours")
    @Tag(name = "warwalking")
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<TourEntity> getAllTours(@HeaderParam("Azusa-License") String license)
    {
        licenseService.validateLicenseThrowing(license);

        List<TourEntity> result = TourEntity.listAll();
        return result;
    }
}
