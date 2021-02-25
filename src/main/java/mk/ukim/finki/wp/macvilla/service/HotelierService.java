package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.*;

import java.util.List;
import java.util.Optional;

public interface HotelierService {

    List<Place> listAllManagedPlaces(Long managerId);

    Optional<Place> findPlace(Long managerId, Long placeId);

    Optional<Place> deletePlace(Long managerId, Long placeId);

    Place addPlace(Long managerId, Long cityId, String name, String description, String address,
                   String telephoneNumber, Integer price, Long categoryId, List<Image> gallery,
                   Image thumbnail);

    Place updatePlace(Long placeId, Long managerId, Long cityId, String name, String description,
                                String address, String telephoneNumber, Integer price, Long categoryId,
                                List<Image> gallery, Image thumbnail);
    Optional<User> findById(Long managerId);
}
