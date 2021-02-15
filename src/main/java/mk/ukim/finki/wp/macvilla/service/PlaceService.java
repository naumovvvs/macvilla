package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Coordinates;
import mk.ukim.finki.wp.macvilla.model.Image;
import mk.ukim.finki.wp.macvilla.model.Place;
import java.util.List;
import java.util.Optional;

public interface PlaceService {
    List<Place> listAllPlaces();
    List<Place> listAllByCityId(Long cityId);
    List<Place> listAllByCategoryId(Long categoryId);
    List<Place> listAllByManagerId(Long managerId);
    Optional<Place> findById(Long placeId);

    // if ascending = false, then we return places in descending order
    List<Place> listAllByRatingGreaterThan(Float rating, boolean ascending);

    Place save(Long managerId, Long cityId, String name, String description, String address,
               String telephoneNumber, Integer price, Long categoryId, List<Image> gallery,
               Image thumbnail, Coordinates map);

    Place update(Long placeId, Long cityId, String name, String description, String address, String telephoneNumber,
                 Integer price, Long categoryId, List<Image> gallery, Image thumbnail, Coordinates map);
}
