package api;

import model.Card;
import model.Purchase;
import model.Refill;
import repository.PurchaseRepository;
import repository.RefillRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

public class PurchasesResource {
    private Card card;

    public PurchasesResource(Card card) {
        this.card = card;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Purchase> all(Map<String, Object> info,
                              @Context PurchaseRepository repository) {
        return repository.findByNumber(card.getNumber());
    }

    @GET
    @Path("{purchase_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Purchase getPurchase(@PathParam("purchase_id") long id,
                                @Context PurchaseRepository repository) {
        return repository.findById(id);
    }

    @GET
    @Path("{purchase_id}/refill")
    @Produces(MediaType.APPLICATION_JSON)
    public Refill getRefill(@PathParam("purchase_id") long id,
                            @Context PurchaseRepository repository,
                            @Context RefillRepository refillRepository) {
        Purchase purchase = getPurchase(id, repository);

        return refillRepository.findByPurchase(purchase);
    }
}
