package api;

import model.Card;
import model.Plan;
import model.PlanRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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
}
