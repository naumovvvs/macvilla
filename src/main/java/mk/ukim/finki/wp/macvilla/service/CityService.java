package mk.ukim.finki.wp.macvilla.service;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.wp.macvilla.model.City;
import mk.ukim.finki.wp.macvilla.model.Place;

public interface CityService {
    Optional<City> findById(Long cityId);

    City save(String name, String description);

    List<City> listAllCities();

    List<Place> listAllPlacesInCity(Long cityId);

    List<City> findAllByName(String name);

    List<City> findByDescriptionLike(String searchText);

}
