package api;

import jersey.Routes;
import model.Card;
import model.Topup;
import repository.TopupRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class TopupsResource {
    private Card card;

    public TopupsResource(Card card) {
        this.card = card;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context TopupRepository repository,
                           @Context Routes routes) {
        long id = repository.create(card, info);

        if (id == 0) {
            return Response.status(400).build();
        }
        return Response.created(routes.topup(repository.findById(id))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Topup> all(@Context TopupRepository topupRepository) {
        return topupRepository.findByNumber(card.getNumber());
    }

    @GET
    @Path("{topup_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Topup get(@PathParam("topup_id") long id,
                     @Context TopupRepository repository) {
        Topup topup = repository.findById(id);
        if (topup != null) {
            return topup;
        }
        throw new NotFoundException();
    }
}
