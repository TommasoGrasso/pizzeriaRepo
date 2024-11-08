package web.service;

import DTO.UserDTO;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserService {

    @WebMethod
    UserDTO getUserById(int id);

    @WebMethod
    List<UserDTO> getAllUsers();

    @WebMethod
    String saveUser(String username, String password);

    @WebMethod
    String deleteUser(int id);

    @WebMethod
    String updateUser(int id, String username, String password);
}