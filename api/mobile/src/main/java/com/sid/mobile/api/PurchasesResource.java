package com.sid.mobile.api;

import com.sid.mobile.model.Card;
import com.sid.mobile.spi.model.Purchase;
import com.sid.mobile.model.Refill;
import com.sid.mobile.spi.repository.PurchaseRepository;
import com.sid.mobile.spi.repository.RefillRepository;

import javax.ws.rs.*;
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
    public Purchase getPurchase(@PathParam("purchase_id") String id,
                                @Context PurchaseRepository repository) {
        Purchase purchase = repository.findById(id);
        if (purchase != null) {
            return purchase;
        }
        throw new NotFoundException();
    }

    @GET
    @Path("{purchase_id}/refill")
    @Produces(MediaType.APPLICATION_JSON)
    public Refill getRefill(@PathParam("purchase_id") String id,
                            @Context PurchaseRepository repository,
                            @Context RefillRepository refillRepository) {
        Purchase purchase = getPurchase(id, repository);

        Refill refill = refillRepository.findByPurchase(purchase);
        if (refill != null) {
            return refill;
        }
        throw new NotFoundException();
    }
}
