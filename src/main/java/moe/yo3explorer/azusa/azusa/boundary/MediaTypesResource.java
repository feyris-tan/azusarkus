package moe.yo3explorer.azusa.azusa.boundary;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import moe.yo3explorer.azusa.azusa.control.MediaRepository;
import moe.yo3explorer.azusa.azusa.entity.MediaTypesEntity;
import moe.yo3explorer.azusa.azusa.entity.ShelvesEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/azusa/mediaTypes")
public class MediaTypesResource {

    @Inject
    Logger logger;

    @Inject
    RestLicenseService licenseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<MediaTypesEntity> getAll(@HeaderParam("Azusa-License") String license)
    {
        licenseService.validateLicenseThrowing(license);

        return MediaTypesEntity.findAll().list();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    @Path("/{id}")
    public MediaTypesEntity findById(@HeaderParam("Azusa-License") String license, @PathParam("id") int id)
    {
        licenseService.validateLicenseThrowing(license);
        return MediaTypesEntity.findById(id);
    }
}
