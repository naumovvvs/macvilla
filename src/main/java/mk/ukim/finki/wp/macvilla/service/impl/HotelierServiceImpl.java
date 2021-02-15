package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.HotelierNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.PlaceNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.HotelierRepository;
import mk.ukim.finki.wp.macvilla.service.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelierServiceImpl implements HotelierService {

    private final HotelierRepository hotelierRepository;
    private final UserService userService;
    private final PlaceService placeService;
    private final CityService cityService;
    private final CategoryService categoryService;

    public HotelierServiceImpl(HotelierRepository hotelierRepository, UserService userService, PlaceService placeService,
                               CityService cityService, CategoryService categoryService) {
        this.hotelierRepository = hotelierRepository;
        this.userService = userService;
        this.placeService = placeService;
        this.cityService = cityService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Place> listAllManagedPlaces(Long managerId) {
        return this.placeService.listAllByManagerId(managerId);
    }

    @Override
    public Optional<Place> findPlace(Long managerId, Long placeId) {
        Optional<User> hotelier = this.userService.findById(managerId);

        if(hotelier.isPresent()){
            return Optional.of(this.placeService
                    .listAllByManagerId(managerId)
                    .stream()
                    .filter(p -> p.getPlaceId().equals(placeId)).collect(Collectors.toList()).get(0));
        } else {
            throw new HotelierNotFoundException(managerId);
        }
    }

    @Override
    public Optional<User> findById(Long managerId) {
        return this.hotelierRepository.findById(managerId);
    }

    @Override
    public Optional<Place> deletePlace(Long managerId, Long placeId) {
        Optional<User> hotelier = this.userService.findById(managerId);

        if(hotelier.isPresent()){
            Optional<Place> managedPlace = this.findPlace(managerId, placeId);
            //managedPlace.ifPresent(place -> this.placeService.removeById(managedPlace.get().getPlaceId()));
            //TODO: Finish when place service changes are pushed

            return managedPlace;
        } else {
            throw new HotelierNotFoundException(managerId);
        }
    }

    @Override
    public Place addPlace(Long managerId, Long cityId, String name, String description, String address,
                          String telephoneNumber, Integer price, Long categoryId, List<Image> gallery,
                          Image thumbnail, Coordinates map) {
        Optional<User> hotelier = this.userService.findById(managerId);

        if(hotelier.isPresent()){
            City city = this.cityService.findById(cityId).
                    orElseThrow(() -> new CityNotFoundException(cityId));

            Category category = this.categoryService.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId));

            //return this.placeService.save(hotelier, city, name, description, address, telephoneNumber,
            //        price, category, gallery, thumbnail, map);
            //TODO: Finish when place service changes are pushed
            return null;
        } else {
            throw new HotelierNotFoundException(managerId);
        }
    }

    @Override
    public Place updatePlace(Long placeId, Long managerId, Long cityId, String name, String description,
                                       String address, String telephoneNumber, Integer price, Long categoryId,
                                       List<Image> gallery, Image thumbnail, Coordinates map) {

        Optional<User> hotelier = this.userService.findById(managerId);

        if(hotelier.isPresent()){
            Place managedPlace = this.findPlace(managerId, placeId)
                    .orElseThrow(() -> new PlaceNotFoundException(placeId));

            Category category = this.categoryService
                    .findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
            managedPlace.setCategory(category);

            City city = this.cityService
                    .findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
            managedPlace.setCity(city);

            managedPlace.setName(name);
            managedPlace.setDescription(description);
            managedPlace.setAddress(address);
            managedPlace.setTelephoneNumber(telephoneNumber);
            managedPlace.setPrice(price);
            managedPlace.setGallery(gallery);
            managedPlace.setThumbnail(thumbnail);
            managedPlace.setMap(map);

            return managedPlace;
        } else {
            throw new HotelierNotFoundException(managerId);
        }
    }
}
