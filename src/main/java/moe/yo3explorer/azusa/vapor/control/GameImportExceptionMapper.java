package moe.yo3explorer.azusa.vapor.control;

import org.jetbrains.annotations.NotNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GameImportExceptionMapper implements ExceptionMapper<GameImportException> {
    @Override
    public Response toResponse(@NotNull GameImportException e) {
        return Response.status(422).entity(e.getMessage()).build();
    }
}
