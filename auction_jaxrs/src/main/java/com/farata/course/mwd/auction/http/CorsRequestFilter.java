package com.farata.course.mwd.auction.http;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by ds on 01/02/15.
 */
@Provider
public class CorsRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

    }
}
