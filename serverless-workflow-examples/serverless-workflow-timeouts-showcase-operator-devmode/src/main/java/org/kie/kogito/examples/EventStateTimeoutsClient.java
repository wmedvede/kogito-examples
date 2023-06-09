package org.kie.kogito.examples;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.kie.kogito.examples.EventStateTimeoutsClient.CONFIG_KEY;

@Path("/")
@RegisterRestClient(configKey = CONFIG_KEY)
public interface EventStateTimeoutsClient extends WorkflowClient {

    String ID = "event_state_timeouts";
    String URI = "/" + ID;
    String CONFIG_KEY = "event_state_timeouts";

    @POST
    @Path(URI)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response post(@Context HttpHeaders httpHeaders, @QueryParam("businessKey") @DefaultValue("") String businessKey, String input);

    @GET
    @Path(URI)
    @Produces(MediaType.APPLICATION_JSON)
    Response get();

}

