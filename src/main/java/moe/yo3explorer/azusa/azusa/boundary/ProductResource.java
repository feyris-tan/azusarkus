package moe.yo3explorer.azusa.azusa.boundary;

import moe.yo3explorer.azusa.azusa.control.MediaRepository;
import moe.yo3explorer.azusa.azusa.control.ProductRepository;
import moe.yo3explorer.azusa.azusa.entity.MediaEntity;
import moe.yo3explorer.azusa.azusa.entity.ProductInShelf;
import moe.yo3explorer.azusa.azusa.entity.ProductsEntity;
import moe.yo3explorer.azusa.web.boundary.RestLicenseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/azusa/products")
public class ProductResource {
    @Inject
    RestLicenseService restLicenseService;

    @Inject
    ProductRepository productRepository;

    @Inject
    MediaRepository mediaRepository;

    @Inject
    MediaTypesResource mediaTypesResource;

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

    @GET
    @Path("/list/full.json")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<ProductsEntity> findAll(@HeaderParam("Azusa-License") String license)
    {
        restLicenseService.validateLicenseThrowing(license);
        List<ProductsEntity> all = productRepository.findAll();
        return all;
    }


    @GET
    @Path("/inshelf/{shelfId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "azusa")
    public List<ProductInShelf> findInShelf(@HeaderParam("Azusa-License") String lic, @PathParam("shelfId") int shelfId)
    {
        List<ProductsEntity> inShelfRaw = findInShelfRaw(lic, shelfId);

        return inShelfRaw.stream().map(x -> {
            ProductInShelf result = new ProductInShelf();
            result.Id = x.id;
            result.Name = x.name;
            result.Price = x.price;
            result.BoughtOn = x.boughtOn;
            result.ScreenshotSize = productRepository.findScreenshotLength(x.id);
            result.CoverSize = productRepository.findCoverLength(x.id);
            result.NSFW = x.nsfw;

            List<MediaEntity> mediaKeysByProduct = mediaRepository.findKeysByProduct(result.Id);
            if (mediaKeysByProduct.size() > 0) {
                result.IconId = mediaKeysByProduct.get(0).mediatypeid;
                result.NumberOfDiscs = mediaKeysByProduct.size();
                Optional<MediaEntity> anyUndumped = mediaKeysByProduct.stream().filter(y -> (y.dumppath == null) || (y.dumppath.equals(""))).findAny();
                if (anyUndumped.isPresent())
                    result.ContainsUndumped = true;
                result.MissingGraphData = mediaKeysByProduct.stream().filter(y -> (y.graphdata == null) || (y.graphdata.equals(""))).map(y -> mediaTypesResource.findById(lic,y.mediatypeid)).filter(y -> y.graphdata).count();
            }

            return result;
        }).sorted(Comparator.comparing(x -> x.Name)).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Tag(name = "azusa")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductsEntity findById(@HeaderParam("Azusa-License") String lic, @PathParam("id") int pid)
    {
        restLicenseService.validateLicenseThrowing(lic);
        return ProductsEntity.findById(pid);
    }
}
