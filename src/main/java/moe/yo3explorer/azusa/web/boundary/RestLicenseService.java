package moe.yo3explorer.azusa.web.boundary;

import moe.yo3explorer.azusa.web.control.LicenseNotFoundException;
import moe.yo3explorer.azusa.web.control.LicenseNotProvidedException;
import moe.yo3explorer.azusa.web.control.LicenseRevokedException;
import moe.yo3explorer.azusa.web.entity.RestLicenseEntity;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RestLicenseService
{
    @Inject
    Logger logger;

    public boolean validateLicense(String licData)
    {
        if (licData == null)
            return false;

        if (licData.equals(""))
            return false;

        Optional<RestLicenseEntity> license = RestLicenseEntity.find("license = ?1", licData).firstResultOptional();
        if (!license.isPresent())
            return false;

        if (license.get().banned)
            return false;

        return true;
    }

    public RestLicenseEntity validateLicenseThrowing(String licData)
    {
        if (licData == null)
            throw new LicenseNotProvidedException();

        if (licData.equals(""))
            throw new LicenseNotProvidedException();

        Optional<RestLicenseEntity> license = RestLicenseEntity.find("license = ?1", licData).firstResultOptional();
        if (!license.isPresent())
            throw new LicenseNotFoundException();

        if (license.get().banned)
            throw new LicenseRevokedException();

        return license.get();
    }

}
