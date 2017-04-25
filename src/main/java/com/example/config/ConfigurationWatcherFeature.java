package com.example.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;


/**
 * @author Paul Samsotha.
 */
@Provider
public class ConfigurationWatcherFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(ConfigurationWatcherApplicationListener.class);
        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                // bind ConfigurationService to the contract
                // ConfigurationService, which is the contract injected into the filter,
                // and ConfigurationChangeListener which is injected into the application
                // event listener.
                bind(ConfigurationService.class)
                        .to(ConfigurationService.class)
                        .to(ConfigurationChangeListener.class)
                        .in(Singleton.class);
            }
        });
        return true;
    }
}
