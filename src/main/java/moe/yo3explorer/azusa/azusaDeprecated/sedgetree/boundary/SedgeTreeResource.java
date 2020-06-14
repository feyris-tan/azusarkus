package moe.yo3explorer.azusa.azusaDeprecated.sedgetree.boundary;

import io.quarkus.panache.common.Sort;
import moe.yo3explorer.azusa.azusaDeprecated.sedgetree.entity.VersionEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sedgetree")
public class SedgeTreeResource
{
    @Inject
    RestLicenseService licenseService;

    @Path("/latestVersion")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "sedgetree")
    public int getLatestVersion(@HeaderParam("Azusa-License") String lic)
    {
        licenseService.validateLicenseThrowing(lic);

        VersionEntity ve = VersionEntity.findAll(Sort.descending("id")).firstResult();
        return ve.id;
    }
}
