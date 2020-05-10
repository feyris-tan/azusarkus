package moe.yo3explorer.azusa.vapor.boundary;

import jp.gr.java_conf.dangan.util.lha.LhaInputStream;
import moe.yo3explorer.azusa.vapor.control.*;
import moe.yo3explorer.azusa.vapor.entity.Game;
import moe.yo3explorer.azusa.vapor.entity.MapFile;
import moe.yo3explorer.azusa.vapor.entity.ResourceFile;
import moe.yo3explorer.azusa.vapor.entity.ZBlob;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.*;

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

    @Inject
    ZBlobService zBlobService;

    @Inject
    ExFontService exFontService;

    @Inject
    MapService mapService;

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

    // http://localhost:8080/vapor/play/games/moem62594/index.json
    @GET
    @Path("/play/games/{sku}/index.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIndexJson(@PathParam("sku") String sku)
    {
        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
             return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(gameService.buildIndexJson(gameBySku)).build();
    }

    @GET
    @Path("/play/games/{sku}/RPG_RT.ldb")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getRpgRtLdb(@PathParam("sku") String sku)
    {
        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(gameBySku.lcfdatabase).build();
    }

    // http://localhost:8080/vapor/play/games/moem62594/RPG_RT.ini
    @GET
    @Path("/play/games/{sku}/RPG_RT.ini")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getRpgRtIni(@PathParam("sku") String sku)
    {
        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        StringWriter iniWriterL1 = new StringWriter();
        iniWriterL1.write(String.format("[RPG_RT]\r\n"));
        iniWriterL1.write(String.format("GameTitle=%s\r\n",gameBySku.gametitle));

        if (gameBySku.mapeditmode != null)
            iniWriterL1.write(String.format("MapEditMode=%d\r\n",gameBySku.mapeditmode));

        if (gameBySku.mapeditzoom != null)
            iniWriterL1.write(String.format("MapEditZoom=%d\r\n", gameBySku.mapeditzoom));

        iniWriterL1.write(String.format("FullPackageFlag=1\r\n"));

        if (gameBySku.knownversion != null)
            iniWriterL1.write(String.format("KnownVersion=%d\r\n",gameBySku.knownversion));

        return Response.ok(iniWriterL1.toString()).build();
    }

    @GET
    @Path("/play/games/{sku}/RPG_RT.lmt")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getRpgRtLmt(@PathParam("sku") String sku)
    {
        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(gameBySku.lcfmaptree).build();
    }

    @GET
    @Path("/play/games/{sku}/{category}/{resource}")
    public Response getResource(@PathParam("sku") String sku, @PathParam("category") String category, @PathParam("resource") String resource)
    {
        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        ResourceFile resourceFile = gameService.findResourceFile(gameBySku, category, resource);
        ZBlob resultZBlob = zBlobService.findZBlobById(resourceFile.zblobid);
        MediaType mediaType = guessMediaType(resourceFile);

        boolean cmp = resultZBlob.blob[0] == 0x1f;

        return Response.ok(resultZBlob.blob,mediaType)
                .encoding(cmp ? "gzip" : "identity")
                .build();
    }

    @GET
    @Path("/play/games/{sku}/ExFont")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getExFont(@PathParam("sku") String sku)
    {
        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        if (gameBySku.exfont != null)
            return Response.ok(gameBySku.exfont).build();
        else
            return Response.ok(exFontService.getExfont()).build();
    }

    // http://localhost:8080/vapor/play/index.html?game=YDYW85828

    private MediaType guessMediaType(ResourceFile resourceFile)
    {
        String resPath = resourceFile.filename.toLowerCase();
        if (resPath.endsWith(".png"))
            return new MediaType("image","png");
        else if (resPath.endsWith(".wav"))
            return new MediaType("audio","wav");
        else if (resPath.endsWith(".mid"))
            return new MediaType("audio","x-midi");
        else
            return new MediaType("application","octet-stream");
    }

    @GET
    @Path("/play/games/{sku}/{mapkey}.lmu")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getMap(@PathParam("sku") String sku, @PathParam("mapkey") String mapkey)
    {
        mapkey = mapkey.toLowerCase();
        if (!mapkey.startsWith("map"))
            return Response.status(Response.Status.BAD_REQUEST).build();

        Game gameBySku = gameService.findGameBySku(sku);
        if (gameBySku == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        mapkey = mapkey.substring(3);
        short mapId = Short.parseShort(mapkey);
        MapFile mapFile = mapService.findMapFile(gameBySku, mapId);

        return Response.ok(mapFile.mapdata).build();
    }
}
