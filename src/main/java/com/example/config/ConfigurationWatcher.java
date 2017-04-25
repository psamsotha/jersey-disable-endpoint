package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * @author Paul Samsotha.
 */
public class ConfigurationWatcher {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final List<ConfigurationChangeListener> listeners = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private final Path dir = Paths.get(".", "config");

    private WatchService watch;


    void addListener(ConfigurationChangeListener listener) {
        this.listeners.add(listener);
    }

    void handleChangeDetected(Configuration configuration) {
        this.listeners.forEach(listener -> listener.onChange(configuration));
    }

    void startWatching() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                this.watch = this.dir.getFileSystem().newWatchService();
                this.dir.register(watch, StandardWatchEventKinds.ENTRY_MODIFY);
                this.logger.info("watch started for dir: " + this.dir);

                WatchKey key;
                while (true) {
                    if (this.watch == null) {
                        break;
                    }
                    key = this.watch.take();
                    key.pollEvents().stream().forEach(event -> {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            final Configuration configuration = extractConfiguration(event);
                            handleChangeDetected(configuration);
                        }
                    });
                    key.reset();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Configuration extractConfiguration(WatchEvent event) {
        try {
            final WatchEvent<Path> evt = (WatchEvent<Path>) event;
            final Path path = this.dir.resolve(evt.context());
            final Configuration configuration = this.mapper.readValue(path.toFile(), Configuration.class);
            return configuration;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void cancelWatch() {
        synchronized (this.watch) {
            try {
                this.watch.close();
                this.logger.info("watch on dir " + this.dir + " now closed.");
                this.watch = null;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
