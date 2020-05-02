package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.azusa.control.ProductRepository;
import moe.yo3explorer.azusa.azusa.entity.ProductInShelf;
import moe.yo3explorer.azusa.azusa.entity.ProductsEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/azusa/products")
public class ProductResource {
    @Inject
    RestLicenseService restLicenseService;

    @Inject
    ProductRepository productRepository;

    @GET
    @Path("/inshelfRaw/{shelfId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<ProductsEntity> findInShelfRaw(@HeaderParam("Azusa-License") String license, @PathParam("shelfId") int shelfId)
    {
        restLicenseService.validateLicenseThrowing(license);
        List<ProductsEntity> byShelf = productRepository.findByShelf(shelfId);
        return byShelf;
    }

    /*
    @GET
    @Path("/inshelf/{shelfId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<ProductInShelf> findInShelf(@HeaderParam("Azusa-License") String lic, @PathParam("shelfId") int shelfId)
    {
        List<ProductsEntity> inShelfRaw = findInShelfRaw(lic, shelfId);

        inShelfRaw.stream().map(x -> {
            ProductInShelf pis = new ProductInShelf();
            pis.Id = x.id;

        })
    }
    */
}
