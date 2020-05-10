package moe.yo3explorer.azusa.vapor.boundary;

import jp.gr.java_conf.dangan.util.lha.LhaInputStream;
import moe.yo3explorer.azusa.vapor.control.EasyRpgPlayerService;
import moe.yo3explorer.azusa.vapor.control.GameImporter;
import moe.yo3explorer.azusa.vapor.control.GameService;
import moe.yo3explorer.azusa.vapor.entity.Game;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/vapor")
@Tag(name = "vapor",description = "easyRPG Kollektionen für's Web!")
@VaporRelated
public class VaporResource {

    @Inject
    GameImporter gameImporter;

    @Inject
    Logger logger;

    @Inject
    EasyRpgPlayerService easyRpg;

    @Inject
    GameService gameService;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping()
    {
        return "Pong!";
    }

    @POST
    @Path("/game")
    @Consumes("application/x-lzh-compressed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadGame(InputStream is) throws IOException {
        //LhaInputStream lha = new LhaInputStream(is);
        LhaInputStream lha = new LhaInputStream(is);
        Game game = gameImporter.tryImportGame(lha);

        return Response.ok()
                .header("Location", UriBuilder.fromResource(VaporResource.class).path(String.format("/index.html?game=%s",game.vapor_sku)).build())
                .header("X-Azusa-Remark","A")
                .build();
    }

    @GET
    @Path("/play/index.html")
    @Produces("text/html")
    public Response getIndexHtml(@QueryParam("game") String sku)
    {
        if (sku == null || sku.equals(""))
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Provide an SKU, please!")
                    .build();

        if (!gameService.testForSku(sku))
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("The provided SKU is not known!")
                    .build();

        return Response.ok(easyRpg.getHtml()).build();
    }

    @GET
    @Path("/play/index.js")
    @Produces("text/javascript")
    public Response getIndexJs()
    {
        return Response.ok(easyRpg.getJs()).build();
    }

    @GET
    @Path("/play/index.wasm")
    @Produces("application/wasm")
    public Response getIndexWasm()
    {
        return Response.ok(easyRpg.getWasm()).build();
    }

    // http://localhost:8080/vapor/play/index.html?game=MOEM62594
    // http://localhost:8080/vapor/play/games/moem62594/index.json
    @GET
    @Path("/play/games/{sku}/index.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIndexJson(@PathParam("sku") String sku)
    {
        sku = sku.toUpperCase();
        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
             return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(gameService.buildIndexJson(gameBySku)).build();
    }
}
