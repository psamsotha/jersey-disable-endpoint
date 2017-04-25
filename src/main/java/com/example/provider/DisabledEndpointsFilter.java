package com.example.provider;

import com.example.Main;
import com.example.config.ConfigurationService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;


/**
 * @author Paul Samsotha.
 */
@Provider
@PreMatching
public class DisabledEndpointsFilter implements ContainerRequestFilter {

    @Inject
    private ConfigurationService configuration;


    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        final List<String> disabledEndpoints = this.configuration.getDisabledEndpoints();
        final String path = stripLeadingSlash(request.getUriInfo().getPath());

        for (String endpoint: disabledEndpoints) {
            endpoint = stripLeadingSlash(endpoint);
            if (path.startsWith(endpoint)) {
                request.abortWith(Response.status(404).build());
                return;
            }
        }
    }

    private String stripLeadingSlash(String path) {
        return path.charAt(0) == '/'
                ? path.substring(1)
                : path;
    }
}
