package moe.yo3explorer;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.Random;

public class RandomProducer
{
    private Random rng;
    private static Logger logger;

    @Produces
    public Random produceRandom(InjectionPoint injectionPoint)
    {
        if (logger == null)
        {
            logger = Logger.getLogger(getClass());
        }
        if (rng == null)
        {
            rng = new Random();
            logger.info("Initialize the random number generator.");
        }
        logger.infof("Injecting the random number generator into: %s", injectionPoint.getMember().getDeclaringClass().getSimpleName());
        return rng;
    }
}
