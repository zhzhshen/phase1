package api;

import model.UserRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Path("users")
public class UsersResource {

    @Path("{uid}")
    public UserResource getUser(@PathParam("uid") long uid,
                                @Context UserRepository userRepository) {
        return new UserResource(userRepository.findUserById(uid));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Map<String, Object> info,
                           @Context UserRepository userRepository) throws URISyntaxException {
        long uid = userRepository.create(info);

        if (info.get("password").toString().trim().isEmpty() || info.get("username").toString().trim().isEmpty()) {
            return Response.status(400).build();
        }

        if (userRepository.findUserById(uid) != null) {
            return Response.created(new URI("/users/1")).build();
        } else {
            return Response.status(400).build();
        }
    }
}
