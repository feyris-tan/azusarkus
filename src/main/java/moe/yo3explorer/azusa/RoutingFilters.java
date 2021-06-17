package moe.yo3explorer.azusa;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class RoutingFilters {

    @Inject
    Logger logger;

    @RouteFilter(100)
    public void myFilter(@NotNull RoutingContext rc)
    {
        rc.response().putHeader("X-Azusa-Filter","1");
        logger.infof("Got request for: %s",rc.request().path());
        rc.next();
    }
}
