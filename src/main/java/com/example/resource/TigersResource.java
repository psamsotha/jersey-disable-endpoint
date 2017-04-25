package com.example.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


/**
 * @author Paul Samsotha.
 */
@Path("tigers")
public class TigersResource {

    @GET
    public String get() {
        return "Tigers";
    }
}
