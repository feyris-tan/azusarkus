package moe.yo3explorer.azusa.vapor.boundary;

import jp.gr.java_conf.dangan.util.lha.LhaInputStream;
import moe.yo3explorer.azusa.vapor.control.GameImporter;
import moe.yo3explorer.azusa.vapor.entity.Game;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
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
}
