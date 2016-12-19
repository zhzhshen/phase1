import models.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/users")
public class UsersResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> all() {
        return Arrays.asList(new User("Sid", "Shen"));
    }

    @Path("/1")
    @Produces(MediaType.APPLICATION_JSON)
    public User get() {
        return new User("Sid", "Shen");
    }
}
