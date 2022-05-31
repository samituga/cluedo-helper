package io.github.samituga.cluedohelperapi.config.logger;

import static io.github.samituga.cluedohelperapi.annotation.LoggerQualifier.LoggerName.EXTERNAL_REQUEST;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import io.github.samituga.cluedohelperapi.annotation.LoggerQualifier;
import io.github.samituga.cluedohelperapi.util.LogManagerWrapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Configuration class to facilitate the injection of a {@link Logger} bean.
 */
@Configuration
public class LoggerConfig {

    /**
     * {@link Logger} bean to be injected.
     *
     * @param injectionPoint the point in code where the {@link Logger} is being injected
     * @return A {@link Logger} for the respective class
     */
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    Logger logger(InjectionPoint injectionPoint) {
        return LogManagerWrapper.getLogger(injectionPoint);
    }

    @Bean
    @LoggerQualifier(loggerName = EXTERNAL_REQUEST)
    public Logger externalRequestLogger() {
        return LogManagerWrapper.getLogger(EXTERNAL_REQUEST.toString());
    }
}
