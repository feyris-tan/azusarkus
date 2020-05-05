package moe.yo3explorer.azusa.vapor.boundary;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

@VaporRelated
@Provider
public class VaporAuthorizationInterceptor implements ContainerRequestFilter {

    @Inject
    Logger logger;

    private Response forbidden;

    private void buildUsefulResponses()
    {
        forbidden = Response
                .status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic realm=\"Vapor\"")
                .build();
    }


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (forbidden == null)
            buildUsefulResponses();

        String authorization = containerRequestContext.getHeaderString("Authorization");
        if (authorization == null || authorization.equals(""))
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }

        String[] authArgs = authorization.split(" ");
        if (authArgs.length != 2)
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }

        if (!authArgs[0].toLowerCase().equals("basic"))
        {
            logger.infof("Weird auth scheme: %s",authArgs[1]);
            containerRequestContext.abortWith(forbidden);
            return;
        }

        byte[] decoded = Base64.getDecoder().decode(authArgs[1]);
        String cmdline = new String(decoded);
        String[] args = cmdline.split(":");
        if (args.length != 2)
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }
        logger.info("user: " + args[0]);
        logger.info("pass: " + args[1]);

        if (!args[0].equals("ft"))
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }

        if (!args[1].equals("12345"))
        {
            containerRequestContext.abortWith(forbidden);
            return;
        }

        logger.infof("Accepted Vapor Authorization for: %s",args[0]);
        return;
    }
}
