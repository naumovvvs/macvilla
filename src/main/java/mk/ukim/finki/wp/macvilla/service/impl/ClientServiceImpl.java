package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.enums.Role;
import mk.ukim.finki.wp.macvilla.model.exceptions.ClientNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.macvilla.repository.ClientRepository;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Place> removeFromFavoritePlaces(Client client, Place place) {
        List<Place> favorites = client.getFavoritePlaces();
        favorites.remove(place);

        client.setFavoritePlaces(favorites);

        this.clientRepository.save(client);

        return Optional.of(place);
    }

    @Override
    public Optional<Place> addToFavoritePlaces(Client client, Place place) {
        List<Place> favorites = client.getFavoritePlaces();
        favorites.add(place);

        client.setFavoritePlaces(favorites);

        this.clientRepository.save(client);

        return Optional.of(place);
    }

    @Override
    public Optional<Client> findByAddress(String address) {
        return this.clientRepository.findByAddress(address);
    }

    @Override
    public Optional<Client> findByAddressLike(String address) {
        return this.clientRepository.findByAddressLike(address);
    }

    @Override
    public User findById(Long id) {
        return this.clientRepository
                .findById(id).filter(u -> u.getRole().equals(Role.ROLE_CLIENT))
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public Client update(Long id, String username, String password, String name, String surname, String email,
                         String avatarURL, String birthDate, String address) {
        // check only for avatar, the other params are required
        if (avatarURL == null || avatarURL.isEmpty()) {
            avatarURL = "";
        }

        Client client = (Client) this.clientRepository
                .findById(id).orElseThrow(() -> new ClientNotFoundException(id));

        if (!passwordEncoder.matches(password, client.getPassword()) && !password.isEmpty()) {
            client.setPassword(passwordEncoder.encode(password));
        }

        client.setUsername(username);
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setAvatarURL(avatarURL);

        LocalDate date = LocalDate.parse(birthDate);
        client.setBirthDate(date);
        client.setAddress(address);

        return this.clientRepository.save(client);
    }
}
