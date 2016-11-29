package api;

import model.CardRepository;
import model.Session;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

@Path("numbers")
public class NumbersResource {

    @Path("{number}")
    public NumberResource get(@PathParam("number") String number,
                              @Context CardRepository cardRepository,
                              @Context Session session) {
        if (session.validate()) {
            return new NumberResource(cardRepository.findByNumber(number));
        }
        return null;
    }
}