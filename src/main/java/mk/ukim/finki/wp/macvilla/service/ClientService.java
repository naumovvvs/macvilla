package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.User;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<Place> removeFromFavoritePlaces(Client client, Place place);
    Optional<Place> addToFavoritePlaces(Client client, Place place);
    Optional<Client> findByAddress(String address);
    Optional<Client> findByAddressLike(String address);
    User findById(Long id);
    List<User> findAllBlockedUsers();
    User save(Long id, String username, String password, String name, String surname, String email,
              String avatarURL, String birthDate, String address);
}
