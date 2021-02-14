package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String username, String password);
    Optional<User> register(String username, String password, String name, String surname, String email, String avatarURL);
    Optional<User> delete(String username);
    Optional<User> update(Long userId, String username, String password, String name, String surname, String email, String avatarURL);
    Optional<User> findById(Long userId);
}
