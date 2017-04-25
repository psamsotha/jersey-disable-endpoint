package com.example.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


/**
 * @author Paul Samsotha.
 */
@Path("lions")
public class LionsResource {

    @GET
    public String get() {
        return "Lions";
    }
}
