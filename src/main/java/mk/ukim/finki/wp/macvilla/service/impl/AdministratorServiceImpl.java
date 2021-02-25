package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Administrator;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.exceptions.AdministratorNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.AdministratorRepository;
import mk.ukim.finki.wp.macvilla.service.AdministratorService;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;

    public AdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public User findById(Long id) {
        return this.administratorRepository.findById(id).orElseThrow(() -> new AdministratorNotFoundException(id));
    }

    @Override
    public User save(Long id, String username, String password, String name, String surname,
                     String email, String avatarURL) {
        this.administratorRepository.deleteById(id);
        User administrator = new Administrator(username, password, name, surname, email, avatarURL);
        return this.administratorRepository.save(administrator);
    }
}
