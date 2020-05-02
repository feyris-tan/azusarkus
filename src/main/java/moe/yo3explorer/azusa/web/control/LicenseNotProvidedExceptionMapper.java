package moe.yo3explorer.azusa.web.control;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LicenseNotProvidedExceptionMapper implements ExceptionMapper<LicenseNotProvidedException> {
    @Override
    public Response toResponse(LicenseNotProvidedException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("No license provided!")
                .header("Content-Type","application/text")
                .build();
    }
}
