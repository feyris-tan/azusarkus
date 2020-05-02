package moe.yo3explorer.azusa;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "Azusa",
                version = "1.0",
                contact = @Contact(
                        name = "Feyris-Tan",
                        url = "https://github.com/feyris-tan",
                        email = "api@feyris-tan.lebtimnetz.de"),
                license = @License(name = "proprietary")
        )
)
public class AzusarkusApplication extends Application {
}
