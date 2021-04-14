package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> login(String username, String password);
    Optional<User> register(String username, String password, String name, String surname, String email,
                            String avatarURL, String birthday, String address, String role);
    Optional<User> registerSocial(User user);
    Optional<User> delete(String username);
    Optional<User> update(Long userId, String username, String password, String name, String surname, String email, String avatarURL);
    Optional<User> findById(Long userId);
    boolean checkIfUsernameExists(String username);
    List<User> findAllBlockedUsers();
    Optional<User> findByUsername(String username);

    @Override
    default UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
