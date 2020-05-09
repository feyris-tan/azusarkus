package moe.yo3explorer.azusa.vapor.control;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GameImporterDuplicateExceptionMapper implements ExceptionMapper<GameImporterDuplicateException> {
    @Override
    public Response toResponse(GameImporterDuplicateException e) {
        return Response.status(Response.Status.CONFLICT).build();
    }
}
