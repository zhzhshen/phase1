package api;

import model.Card;
import spi.model.Usage;
import spi.repository.UsageRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

public class UsagesResource {
    private Card card;

    public UsagesResource(Card card) {
        this.card = card;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usage> all(Map<String, Object> info,
                           @Context UsageRepository repository) {
        return repository.findByNumber(card.getNumber());
    }

    @GET
    @Path("{usage_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usage getPurchase(@PathParam("usage_id") long id,
                                @Context UsageRepository repository) {
        Usage usage = repository.findById(id);
        if (usage != null) {
            return usage;
        }
        throw new NotFoundException();
    }

}
