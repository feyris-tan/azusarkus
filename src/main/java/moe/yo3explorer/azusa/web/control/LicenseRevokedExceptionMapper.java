package moe.yo3explorer.azusa.web.control;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LicenseRevokedExceptionMapper implements ExceptionMapper<LicenseRevokedException> {
    @Override
    public Response toResponse(LicenseRevokedException e) {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("Your license has been revoked!")
                .build();
    }
}
