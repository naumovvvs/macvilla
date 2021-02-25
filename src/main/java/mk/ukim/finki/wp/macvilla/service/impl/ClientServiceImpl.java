package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.exceptions.ClientNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.ClientRepository;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
        return this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public List<User> findAllBlockedUsers() {
        return this.clientRepository.findAll().stream().filter(User::isBlocked).collect(Collectors.toList());
    }

    @Override
    public User save(Long id, String username, String password, String name, String surname, String email,
                     String avatarURL, String birthDate, String address) {
        this.clientRepository.deleteById(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(birthDate);
        } catch (ParseException exception) {
//            TODO:
        }
        User client = new Client(username, password, name, surname, email, avatarURL, date, address);
        return this.clientRepository.save(client);
    }
}
