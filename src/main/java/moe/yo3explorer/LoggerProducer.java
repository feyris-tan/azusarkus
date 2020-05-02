package moe.yo3explorer;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerProducer
{
    private Logger selfLogger;

    @Produces
    public Logger getLogger(@org.jetbrains.annotations.NotNull InjectionPoint injectionPoint)
    {
        if (selfLogger == null)
        {
            selfLogger = Logger.getLogger(getClass());
            selfLogger.info("Start up the logger producer.");
        }
        Class<?> declaringClass = injectionPoint.getMember().getDeclaringClass();
        Logger result = Logger.getLogger(declaringClass);
        result.infof("Producing logger for: %s",declaringClass.getName());
        return result;
    }
}
