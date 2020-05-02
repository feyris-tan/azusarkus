package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.azusa.entity.ShopsEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/azusa/shops")
public class ShopResourcce {

    @Inject
    RestLicenseService licenseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<ShopsEntity> findAll(@HeaderParam("Azusa-License") String lic)
    {
        licenseService.validateLicenseThrowing(lic);

        return ShopsEntity.findAll().list();
    }
}
