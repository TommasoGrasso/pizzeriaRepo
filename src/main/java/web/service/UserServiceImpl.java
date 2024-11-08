package web.service;

import DTO.UserDTO;
import dao.UserDao;
import model.User;

import javax.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@WebService(endpointInterface = "web.service.UserService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDao();

    @Override
    public UserDTO getUserById(int id) {
        User user = userDao.getUserById(id);
        if (user != null) {
            return new UserDTO(user.getId(), user.getUsername());
        }
        return null; 
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public String saveUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return "Nome utente e password non devono essere vuoti.";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.saveUser(user);
        return "Utente salvato con successo!";
    }

    @Override
    public String deleteUser(int id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            return "Utente non trovato!";
        }
        userDao.deleteUser(id);
        return "Utente eliminato con successo!";
    }

    @Override
    public String updateUser(int id, String username, String password) {
        User user = userDao.getUserById(id);
        if (user == null) {
            return "Utente non trovato!";
        }

        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        userDao.updateUser(user);
        return "Utente aggiornato con successo!";
    }
}