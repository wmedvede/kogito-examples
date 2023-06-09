package org.kie.kogito.examples;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/")
public class WorkflowsProxyResource {

    private static final String CALLBACK_STATE_TIMEOUTS_URI = "callback_state_timeouts";
    private static final String SWITCH_STATE_TIMEOUTS_URI = "switch_state_timeouts";
    private static final String EVENT_STATE_TIMEOUTS_URI = "event_state_timeouts";

    @Inject
    @RestClient
    CallbackStateTimeoutsClient callbackStateTimeouts;

    @Inject
    @RestClient
    SwitchStateTimeoutsClient switchStateTimeoutsClient;

    @Inject
    @RestClient
    EventStateTimeoutsClient eventStateTimeoutsClient;

    @POST
    @Path(CALLBACK_STATE_TIMEOUTS_URI)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCS(@Context HttpHeaders httpHeaders, @QueryParam("businessKey") @DefaultValue("") String businessKey, String input) {
        return callbackStateTimeouts.post(httpHeaders, businessKey, input);
    }

    @GET
    @Path(CALLBACK_STATE_TIMEOUTS_URI)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCS() {
        return callbackStateTimeouts.get();
    }

    @POST
    @Path(SWITCH_STATE_TIMEOUTS_URI)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postSS(@Context HttpHeaders httpHeaders, @QueryParam("businessKey") @DefaultValue("") String businessKey, String input) {
        return switchStateTimeoutsClient.post(httpHeaders, businessKey, input);
    }

    @GET
    @Path(SWITCH_STATE_TIMEOUTS_URI)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSS() {
        return switchStateTimeoutsClient.get();
    }

    @POST
    @Path(EVENT_STATE_TIMEOUTS_URI)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postES(@Context HttpHeaders httpHeaders, @QueryParam("businessKey") @DefaultValue("") String businessKey, String input) {
        return eventStateTimeoutsClient.post(httpHeaders, businessKey, input);
    }

    @GET
    @Path(EVENT_STATE_TIMEOUTS_URI)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getES() {
        return eventStateTimeoutsClient.get();
    }
}
