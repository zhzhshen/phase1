package api;

import jersey.Routes;
import model.*;
import spi.model.Purchase;
import spi.model.Session;
import spi.model.Usage;
import spi.repository.PlanRepository;
import spi.repository.PurchaseRepository;
import spi.repository.RefillRepository;
import spi.repository.UsageRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class NumberResource {
    private Card card;

    public NumberResource(Card card) {
        this.card = card;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Card get() {
        return card;
    }

    @GET
    @Path("plan")
    @Produces(MediaType.APPLICATION_JSON)
    public Plan getPlan(@Context PlanRepository planRepository) {
        Plan plan = planRepository.findById(card.getPlanId());
        if (plan != null) {
            return plan;
        }
        throw new NotFoundException();
    }

    @POST
    @Path("/{a:plan-purchases|product-purchases}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPurchase(Map<String, Object> info,
                                       @Context PurchaseRepository repository,
                                       @Context RefillRepository refillRepository,
                                       @Context Session session,
                                       @Context Routes routes) {
        long purchaseId = repository.create(card, info);
        if (purchaseId == 0) {
            return Response.status(400).build();
        }
        Purchase purchase = repository.findById(purchaseId);
        refillRepository.create(purchase);
        return Response.created(routes.purchase(purchase)).build();
    }

    @POST
    @Path("/{a:calls|data-usages|plan-usages}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUsage(Map<String, Object> info,
                                   @Context UsageRepository repository,
                                   @Context Session session,
                                   @Context Routes routes) {
        long usageId = repository.create(card, info);
        if (usageId == 0) {
            return Response.status(400).build();
        }
        Usage usage = repository.findById(usageId);
        return Response.created(routes.usage(usage)).build();
    }

    @Path("purchases")
    public PurchasesResource getPurchases() {
        return new PurchasesResource(card);
    }

    @Path("top-ups")
    public TopupsResource getTopups() {
        return new TopupsResource(card);
    }

    @Path("usages")
    public UsagesResource getUsages() {
        return new UsagesResource(card);
    }
}
