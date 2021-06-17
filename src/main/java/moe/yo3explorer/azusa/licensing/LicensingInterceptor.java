package moe.yo3explorer.azusa.licensing;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Licensed
@Singleton
@Provider
public class LicensingInterceptor implements ContainerRequestFilter {
    @Inject
    Logger logger;

    public RestLicenseEntity validateLicenseThrowing(String licData)
    {
        if (licData == null)
            throw new LicensingException(LicensingStatus.NOT_PROVIDED);

        if (licData.equals(""))
            throw new LicensingException(LicensingStatus.NOT_PROVIDED);

        Optional<RestLicenseEntity> license = RestLicenseEntity.find("license = ?1", licData).firstResultOptional();
        if (!license.isPresent())
            throw new LicensingException(LicensingStatus.NOT_FOUND);

        if (license.get().banned)
            throw new LicensingException(LicensingStatus.REVOKED);

        return license.get();
    }

    private Response forbidden;

    private void buildUsefulResponses()
    {
        forbidden = Response
                .status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Azusa-License realm=\"Azusa\", charset=\"UTF-8\"")
                .build();
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (forbidden == null)
            buildUsefulResponses();

        String licData = containerRequestContext.getHeaderString("Azusa-License");
        if (licData == null || licData.equals(""))
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }

        Optional<RestLicenseEntity> license = RestLicenseEntity.find("license = ?1", licData).firstResultOptional();
        if (!license.isPresent())
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }

        if (license.get().banned)
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }

        return;
    }
}
