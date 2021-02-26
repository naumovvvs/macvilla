package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Hotelier;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.macvilla.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.macvilla.model.exceptions.UsernameTakenException;
import mk.ukim.finki.wp.macvilla.repository.ClientRepository;
import mk.ukim.finki.wp.macvilla.repository.HotelierRepository;
import mk.ukim.finki.wp.macvilla.repository.UserRepository;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final HotelierRepository hotelierRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ClientRepository clientRepository, HotelierRepository hotelierRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.hotelierRepository = hotelierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidUsernameOrPasswordException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password).or(()->Optional.of(new User()));
    }

    @Override
    public Optional<User> register(String username, String password, String name, String surname, String email,
                                   String avatarURL, String birthday, String address, String role) {
        // we don't check if params are provided, because we require them to be provided
        // required in controller, and required in HTML
        // we check only for avatarURL, because it is not required
        if(avatarURL==null || avatarURL.isEmpty()){
            avatarURL = "";
        }

        // check if username exists first, no duplicate usernames allowed
        if(this.userRepository.findByUsername(username).isPresent()){
            throw new UsernameTakenException();
        }

        if(!role.isEmpty() && role.equals("ROLE_CLIENT")){
            LocalDate date = LocalDate.parse(birthday);
            return Optional.of(this.clientRepository.save(new Client(username, passwordEncoder.encode(password), name, surname, email,
                    avatarURL, date, address)));
        }else {
            return Optional.of(this.hotelierRepository.save(
                    new Hotelier(username, passwordEncoder.encode(password), name, surname, email, avatarURL)));
        }
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

        //UserDetails userDetails = this.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, user.getPassword())){
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setUsername(username);
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

    @Override
    public boolean checkIfUsernameExists(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        //if user is not present, the username is valid
        return user.isPresent();
    }

    @Override
    public List<User> findAllBlockedUsers() {
        return this.userRepository.findAllByBlockedTrue();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
