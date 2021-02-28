package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Administrator;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.enums.Role;
import mk.ukim.finki.wp.macvilla.model.exceptions.AdministratorNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.AdministratorRepository;
import mk.ukim.finki.wp.macvilla.service.AdministratorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministratorServiceImpl(AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        return this.administratorRepository
                .findById(id).filter(a -> a.getRole().equals(Role.ROLE_ADMIN))
                .orElseThrow(() -> new AdministratorNotFoundException(id));
    }

    @Override
    public List<User> listAll() {
        return this.administratorRepository
                .findAll().stream().filter(a -> a.getRole().equals(Role.ROLE_ADMIN))
                .collect(Collectors.toList());
    }

    @Override
    public Administrator update(Long id, String username, String password, String name, String surname,
                                String email, String avatarURL) {
        // check only for avatar, the other params are required
        if (avatarURL == null || avatarURL.isEmpty()) {
            avatarURL = "";
        }

        Administrator administrator = (Administrator) this.administratorRepository
                .findById(id).orElseThrow(() -> new AdministratorNotFoundException(id));

        if (!passwordEncoder.matches(password, administrator.getPassword()) && !password.isEmpty()) {
            administrator.setPassword(passwordEncoder.encode(password));
        }

        administrator.setUsername(username);
        administrator.setName(name);
        administrator.setSurname(surname);
        administrator.setEmail(email);
        administrator.setAvatarURL(avatarURL);

        return this.administratorRepository.save(administrator);
    }
}
