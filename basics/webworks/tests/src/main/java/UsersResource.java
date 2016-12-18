import models.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UsersResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User get() {
        return new User("Sid", "Shen");
    }
}
