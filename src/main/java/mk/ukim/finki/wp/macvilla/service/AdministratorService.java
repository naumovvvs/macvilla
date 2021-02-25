package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.User;

public interface AdministratorService {
    User findById(Long id);
    User save(Long id, String username, String password, String name, String surname, String email, String avatarURL);
}
