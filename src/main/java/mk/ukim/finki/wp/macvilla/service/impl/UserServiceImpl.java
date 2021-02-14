package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.macvilla.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.macvilla.repository.UserRepository;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidUsernameOrPasswordException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password).or(()->Optional.of(new User()));
    }

    @Override
    public Optional<User> register(String username, String password, String name, String surname, String email, String avatarURL) {
        // we don't check if params are provided, because we require them to be provided
        // required in controller, and required in HTML
        // we check only for avatarURL, because it is not required
        if(avatarURL==null || avatarURL.isEmpty()){
            avatarURL = "";
        }
        return Optional.of(this.userRepository.save(new User(username, password, name, surname, email, avatarURL)));
    }

    @Override
    public Optional<User> delete(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);

        if(user!=null) {
            this.userRepository.deleteById(user.getUserId());
            return Optional.of(user);
        }else{
            return Optional.of(new User());
        }
    }

    @Override
    public Optional<User> update(Long userId, String username, String password, String name, String surname, String email, String avatarURL) {
        // check only for avatar, the other params are required
        if(avatarURL==null || avatarURL.isEmpty()){
            avatarURL = "";
        }

        User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException(userId));

        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setAvatarURL(avatarURL);

        this.userRepository.save(user);

        return Optional.of(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }
}
