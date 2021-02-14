package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Place> listFavoritePlaces(Client client);
    Optional<Place> removeFromFavoritePlaces(Client client, Place place);
    Optional<Place> addToFavoritePlaces(Client client, Place place);
    Optional<Client> findByAddress(String address);
    Optional<Client> findByAddressLike(String address);
}
