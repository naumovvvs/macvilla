package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Coordinates;
import mk.ukim.finki.wp.macvilla.model.Hotelier;
import mk.ukim.finki.wp.macvilla.model.Place;
import java.util.List;
import java.util.Optional;

public interface HotelierService {
    Hotelier save(String username, String password, String name, String surname, String email, String avatarURL);

    List<Place> listAllManagedPlaces(Long managerId);

    Optional<Place> findPlace(Long managerId, Long placeId);

    Optional<Place> deletePlace(Long managerId, Long placeId);

    Place addPlace(Long managerId, Long cityId, String name, String description, String address,
                   String telephoneNumber, Integer price, Long categoryId,
                   List<String> gallery, String thumbnail, Coordinates map);

    Place updatePlace(Long placeId, Long managerId, Long cityId, String name, String description,
                                String address, String telephoneNumber, Integer price, Long categoryId,
                                List<String> gallery, String thumbnail, Coordinates map);

}
