package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.City;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.CityRepository;
import mk.ukim.finki.wp.macvilla.service.CityService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
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
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
        return city.getListPlaces();
    }

    @Override
    public List<City> findAllByName(String name) {
        return this.cityRepository.findAllByName(name);
    }

    @Override
    public List<City> findByDescriptionLike(String descriptionLike) {
        return this.cityRepository.findAllByDescriptionLike(descriptionLike);
    }
}
