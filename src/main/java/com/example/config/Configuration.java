package com.example.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Samsotha.
 */
public class Configuration {

    private List<String> disabledEndpoints = new ArrayList<>();

    public List<String> getDisabledEndpoints() {
        return this.disabledEndpoints;
    }


    public void setDisabledEndpoints(List<String> disabledEndpoints) {
        this.disabledEndpoints = disabledEndpoints != null
                ? disabledEndpoints : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "disabledEndpoints=" + this.disabledEndpoints +
                '}';
    }
}
