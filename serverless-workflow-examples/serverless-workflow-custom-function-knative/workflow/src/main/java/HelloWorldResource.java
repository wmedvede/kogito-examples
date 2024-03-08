import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.ConfigProvider;

import java.time.OffsetDateTime;

@Path("hello")
public class HelloWorldResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, String body) {
        return "Hello Kubernetes RESTEasy: " + OffsetDateTime.now() + " " + ConfigProvider.getConfig().getConfigValue("value");
    }

}
