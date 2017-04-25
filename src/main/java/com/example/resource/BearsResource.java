package com.example.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


/**
 * @author Paul Samsotha.
 */
@Path("bears")
public class BearsResource {

    @GET
    public String get() {
        return "Bears";
    }
}
