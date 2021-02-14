package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.repository.ClientRepository;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
