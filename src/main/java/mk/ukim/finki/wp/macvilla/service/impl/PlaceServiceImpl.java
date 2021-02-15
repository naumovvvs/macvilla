package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.HotelierNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.PlaceRepository;
import mk.ukim.finki.wp.macvilla.service.CategoryService;
import mk.ukim.finki.wp.macvilla.service.CityService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import mk.ukim.finki.wp.macvilla.service.UserService;
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
    private final UserService userService;

    public PlaceServiceImpl(PlaceRepository placeRepository, CityService cityService, CategoryService categoryService, UserService userService) {
        this.placeRepository = placeRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.userService = userService;
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
    public Place save(Long managerId, Long cityId, String name, String description, String address,
                      String telephoneNumber, Integer price, Long categoryId, List<Image> gallery,
                      Image thumbnail, Coordinates map) {

        Hotelier hotelier = (Hotelier) this.userService.findById(managerId)
                .orElseThrow(() -> new HotelierNotFoundException(managerId));

        Category category = this.categoryService
                .findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));

        City city = this.cityService
                .findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        return this.placeRepository.save(new Place(hotelier, city, name, description, address, telephoneNumber,
                price, category, gallery, thumbnail, map));
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
