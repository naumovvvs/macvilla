package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Administrator;
import mk.ukim.finki.wp.macvilla.model.User;
import java.util.List;

public interface AdministratorService {
    User findById(Long id);
    List<User> listAll();
    Administrator update(Long id, String username, String password, String name, String surname,
                         String email, String avatarURL);
}
