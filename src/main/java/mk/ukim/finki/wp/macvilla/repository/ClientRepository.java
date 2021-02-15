package mk.ukim.finki.wp.macvilla.repository;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends UserRepository {
    List<Place> findAllFavoritePlaces();
    Optional<Client> findByAddress(String address);
    Optional<Client> findByAddressLike(String address);
}
