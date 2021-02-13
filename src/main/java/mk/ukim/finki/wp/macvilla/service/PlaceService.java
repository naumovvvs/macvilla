package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Place;
import java.util.List;

public interface PlaceService {
    List<Place> listAllPlaces();
    List<Place> listAllByCityId(Long cityId);
    List<Place> listAllByCategoryId(Long categoryId);
    List<Place> listAllByManagerId(Long managerId);
    // if ascending = false, then we return places in descending order
    List<Place> listAllByRatingGreaterThan(Float rating, boolean ascending);
}
