package moe.yo3explorer;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import java.util.Random;

public class RandomProducer
{
    private Random rng;
    private static Logger logger;

    @Produces
    public Random produceRandom()
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
        return rng;
    }
}
