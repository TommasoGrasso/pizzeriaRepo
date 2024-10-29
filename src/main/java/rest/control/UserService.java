package rest.control;

import dao.UserDao;
import model.User;
import DTO.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
public class UserService {
    private UserDao userDao = new UserDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getAllUsers() {
        return userDao.getAllUsers().stream()
                      .map(this::convertToDTO)
                      .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getUserById(@PathParam("id") int id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return convertToDTO(user);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userDao.saveUser(user);
        return "User created successfully";
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(@PathParam("id") int id, UserDTO userDTO) {
        userDTO.setId(id);
        User user = convertToEntity(userDTO);
        userDao.updateUser(user);
        return "User updated successfully";
    }

    @DELETE
    @Path("/{id}")
    public String deleteUser(@PathParam("id") int id) {
        userDao.deleteUser(id);
        return "User deleted successfully";
    }

    // si usa dto per evitare di mandare troppi dati e non esporre informazionii
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        return user;
    }
}
