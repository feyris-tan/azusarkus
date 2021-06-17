package moe.yo3explorer.azusa.boundary;

import moe.yo3explorer.azusa.entity.CountriesEntity;
import moe.yo3explorer.azusa.licensing.Licensed;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Licensed
@Path("/countries")
public class CountriesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<CountriesEntity> getAll()
    {
        return CountriesEntity.findAll().list();
    }
}
