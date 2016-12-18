import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UsersResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> get() {
        return new HashMap() {
            {
                put("name", "Sid");
            }
        };
    }
}
