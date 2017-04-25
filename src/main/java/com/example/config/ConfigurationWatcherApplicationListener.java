package com.example.config;

import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.glassfish.jersey.server.monitoring.ApplicationEvent.Type.DESTROY_FINISHED;
import static org.glassfish.jersey.server.monitoring.ApplicationEvent.Type.INITIALIZATION_START;


/**
 * @author Paul Samsotha.
 */
public class ConfigurationWatcherApplicationListener implements ApplicationEventListener {

    private final ConfigurationWatcher watcher = new ConfigurationWatcher();

    /**
     * Inject all services bound with the contract {@link ConfigurationChangeListener}.
     *
     * @see {@link ConfigurationWatcherFeature}.
     */
    @Inject
    private IterableProvider<ConfigurationChangeListener> listeners;


    @PostConstruct
    public void init() {
        this.listeners.forEach(this.watcher::addListener);
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        if (event.getType() == INITIALIZATION_START) {
            this.watcher.startWatching();
        } else if (event.getType() == DESTROY_FINISHED) {
            this.watcher.cancelWatch();
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }
}
