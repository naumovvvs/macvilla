package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Coordinates;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.PlaceRepository;
import mk.ukim.finki.wp.macvilla.service.CategoryService;
import mk.ukim.finki.wp.macvilla.service.CityService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final CityService cityService;
    private final CategoryService categoryService;

    public PlaceServiceImpl(PlaceRepository placeRepository, CityService cityService, CategoryService categoryService) {
        this.placeRepository = placeRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Place> listAllPlaces() {
        return this.placeRepository.findAll();
    }

    @Override
    public Optional<Place> findById(Long placeId) {
        return this.placeRepository.findById(placeId);
    }

    @Override
    public Place save(Long managerId, Long cityId, String name, String description, String address, String telephoneNumber, Integer price, Long categoryId, List<String> gallery, String thumbnail, Coordinates map) {
        //this.userService.findById(managerId).orElseThrow(() -> new UserNotFoundException(managerId));
        //TODO: After user service is created

        this.categoryService
                .findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));

        this.cityService
                .findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        return this.placeRepository.save(new Place(managerId, cityId, name, description, address, telephoneNumber,
                price, categoryId, gallery, thumbnail, map));
    }

    @Override
    public List<Place> listAllByCityId(Long cityId) {
        return this.placeRepository.findAllByCityId(cityId);
    }

    @Override
    public List<Place> listAllByCategoryId(Long categoryId) {
        return this.placeRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Place> listAllByManagerId(Long managerId) {
        return this.placeRepository.findAllByManagerId(managerId);
    }

    @Override
    public List<Place> listAllByRatingGreaterThan(Float rating, boolean ascending) {
        if(ascending){
            // ascending order
            return this.placeRepository.findAllByRatingGreaterThanEqual(rating).stream()
                    .sorted(Comparator.comparing(Place::getRating)).collect(Collectors.toList());
        }else{
            // descending order
            return this.placeRepository.findAllByRatingGreaterThanEqual(rating).stream()
                    .sorted(Comparator.comparing(Place::getRating).reversed()).collect(Collectors.toList());
        }
    }
}
