package api;

import jersey.Routes;
import model.Card;
import model.Plan;
import model.Purchase;
import model.Session;
import repository.PlanRepository;
import repository.PurchaseRepository;
import repository.RefillRepository;

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

    @Path("purchases")
    public PurchasesResource getPurchases() {
        return new PurchasesResource(card);
    }

    @Path("top-ups")
    public TopupsResource getTopups() {
        return new TopupsResource(card);
    }
}
