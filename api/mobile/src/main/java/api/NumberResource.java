package api;

import jersey.Routes;
import model.Card;
import model.Plan;
import model.Purchase;
import model.Session;
import repository.PlanRepository;
import repository.PurchaseRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
        return planRepository.findById(card.getPlanId());
    }

    @POST
    @Path("plan-purchases")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlanPurchase(Map<String, Object> info,
                                       @Context PurchaseRepository repository,
                                       @Context Session session,
                                       @Context Routes routes) {
        long purchaseId = repository.create(card, info);
        if (purchaseId == 0) {
            return Response.status(400).build();
        }
        return Response.created(routes.purchase(repository.findById(purchaseId))).build();
    }

    @POST
    @Path("product-purchases")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProductPurchase(Map<String, Object> info,
                                          @Context PurchaseRepository repository,
                                          @Context Session session,
                                          @Context Routes routes) {
        long purchaseId = repository.create(card, info);
        if (purchaseId == 0) {
            return Response.status(400).build();
        }
        return Response.created(routes.purchase(repository.findById(purchaseId))).build();
    }

    @GET
    @Path("purchases")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Purchase> get(Map<String, Object> info,
                              @Context PurchaseRepository repository,
                              @Context Session session,
                              @Context Routes routes) {
        return repository.findByNumber(card.getNumber());
    }

}
