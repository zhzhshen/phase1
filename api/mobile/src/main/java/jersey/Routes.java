package jersey;

import model.Plan;
import model.Product;
import model.Purchase;
import model.Topup;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

public class Routes {
    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI product(Product product) throws URISyntaxException {
        return URI.create(String.format("%sproducts/%s", baseUri, product.getId()));
    }

    public URI plan(Plan plan) {
        return URI.create(String.format("%splans/%s", baseUri, plan.getId()));
    }

    public URI purchase(Purchase purchase) {
        return URI.create(String.format("%spurchases/%s", baseUri, purchase.getId()));
    }

    public URI topup(Topup topup) {
        return URI.create(String.format("%stop-ups/%s", baseUri, topup.getId()));
    }
}
