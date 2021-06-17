package moe.yo3explorer.azusa.boundary;

import moe.yo3explorer.azusa.licensing.Licensed;
import moe.yo3explorer.azusa.licensing.LicensingInterceptor;
import moe.yo3explorer.azusa.licensing.RestLicenseEntity;
import moe.yo3explorer.azusa.licensing.ValidateResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/startup")
@Tag(name = "Bootstrapping")
public class StartupManagerResource
{
    @Inject
    LicensingInterceptor restLicenseService;

    @GET
    @Path("/validate")
    @Produces(value = "application/json")
    public ValidateResponse validateConnection(@NotNull @Context HttpHeaders headers)
    {
        List<String> requestHeader = headers.getRequestHeader("Azusa-License");
        if (requestHeader.size() == 0)
        {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
        String license = requestHeader.get(0);
        RestLicenseEntity validated = restLicenseService.validateLicenseThrowing(license);

        ValidateResponse validation = new ValidateResponse();
        validation.isValid = true;
        validation.license = validated;

        return validation;
    }

    @GET
    @Path("/ping")
    @Produces(value = MediaType.TEXT_PLAIN)
    public String ping()
    {
        return "pong";
    }

    @GET
    @Path("/ping/licensed")
    @Produces(value = MediaType.TEXT_PLAIN)
    @Licensed
    public String licensePing()
    {
        return "pong";
    }
}
