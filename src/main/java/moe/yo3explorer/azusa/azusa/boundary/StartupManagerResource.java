package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import moe.yo3explorer.azusa.web.entity.RestLicenseEntity;
import moe.yo3explorer.azusa.web.entity.arbitrary.ValidateResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/startup")
public class StartupManagerResource
{
    @Inject
    RestLicenseService restLicenseService;

    @GET
    @Path("/validate")
    @Tag(name = "azusa")
    @Produces(value = "application/json")
    public Response validateConnection(@HeaderParam("Azusa-License") String license)
    {
        RestLicenseEntity validated = restLicenseService.validateLicenseThrowing(license);

        ValidateResponse validation = new ValidateResponse();
        validation.isValid = true;
        validation.license = validated;

        return Response.ok().entity(validation).build();
    }
}
