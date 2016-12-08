package jersey;

import model.*;
import spi.model.Purchase;
import spi.model.Usage;

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

    public URI usage(Usage usage) {
        return URI.create(String.format("%susages/%s", baseUri, usage.getId()));
    }
}
