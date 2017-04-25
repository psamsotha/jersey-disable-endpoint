package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;


/**
 * @author Paul Samsotha.
 */
public class ConfigurationService implements ConfigurationChangeListener {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final AtomicReference<List<String>> disabledEndpoints;


    public ConfigurationService() {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final Configuration config = mapper.readValue(new File("config/app.yml"), Configuration.class);
            this.logger.info("initial config: " + config);
            this.disabledEndpoints = new AtomicReference<>(Collections.unmodifiableList(config.getDisabledEndpoints()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void onChange(Configuration config) {
        this.logger.info("config change: " + config);
        this.disabledEndpoints.set(Collections.unmodifiableList(config.getDisabledEndpoints()));
    }

    public List<String> getDisabledEndpoints() {
        return this.disabledEndpoints.get();
    }
}
