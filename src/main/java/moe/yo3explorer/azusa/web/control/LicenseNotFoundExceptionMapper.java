package moe.yo3explorer.azusa.web.control;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LicenseNotFoundExceptionMapper implements ExceptionMapper<LicenseNotFoundException> {
    @Override
    public Response toResponse(LicenseNotFoundException e) {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("You supplied an invalid license.")
                .build();

    }
}
