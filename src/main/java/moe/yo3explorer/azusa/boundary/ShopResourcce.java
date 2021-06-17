package moe.yo3explorer.azusa.boundary;

import moe.yo3explorer.azusa.entity.ShopsEntity;
import moe.yo3explorer.azusa.licensing.Licensed;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Licensed
@Path("/shops")
public class ShopResourcce {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<ShopsEntity> findAll(@HeaderParam("Azusa-License") String lic)
    {
        return ShopsEntity.findAll().list();
    }
}
