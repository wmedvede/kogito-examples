package org.kie.kogito.examples;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface WorkflowClient {

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Content-Type", value = "application/cloudevents+json")
    Response sendEvent(String event);

}
