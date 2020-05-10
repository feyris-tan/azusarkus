package moe.yo3explorer.azusa.vapor.control;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GameResourceMissingExceptionMapper implements ExceptionMapper<GameResourceMissingException> {
    @Override
    public Response toResponse(GameResourceMissingException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
}
