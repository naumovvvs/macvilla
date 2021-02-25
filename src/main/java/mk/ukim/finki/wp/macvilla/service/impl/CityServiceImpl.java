package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.City;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.repository.CityRepository;
import mk.ukim.finki.wp.macvilla.repository.PlaceRepository;
import mk.ukim.finki.wp.macvilla.service.CityService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;

    public CityServiceImpl(CityRepository cityRepository, PlaceRepository placeRepository) {
        this.cityRepository = cityRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public Optional<City> findById(Long cityId) {
        return this.cityRepository.findById(cityId);
    }

    @Override
    public City save(String name, String description) {
        return this.cityRepository.save(new City(name, description));
    }

    @Override
    public List<City> listAllCities() {
        return this.cityRepository.findAll();
    }

    @Override
    public List<Place> listAllPlacesInCity(Long cityId) {
        return this.placeRepository.findAll().stream().filter(x-> x.getCity().getCityId().equals(cityId)).collect(Collectors.toList());
    }

    @Override
    public List<City> findAllByName(String name) {
        return this.cityRepository.findAllByName(name);
    }

    @Override
    public List<City> findAllByDescriptionLike(String descriptionLike) {
        return this.cityRepository.findAllByDescriptionLike(descriptionLike);
    }
}
