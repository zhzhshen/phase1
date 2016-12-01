package jersey;

import model.Plan;
import model.PlanPrice;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Routes {
    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI plan(Plan plan) {
        return URI.create(String.format("%splans/%s", baseUri, plan.getId()));
    }

    public URI planPrice(PlanPrice planPrice) {
        return URI.create(String.format("%sprices/%s", baseUri, planPrice.getId()));
    }
}
