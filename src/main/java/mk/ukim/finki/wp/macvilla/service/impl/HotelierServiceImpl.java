package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.CityNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.HotelierNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.PlaceNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.HotelierRepository;
import mk.ukim.finki.wp.macvilla.service.CategoryService;
import mk.ukim.finki.wp.macvilla.service.CityService;
import mk.ukim.finki.wp.macvilla.service.HotelierService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelierServiceImpl implements HotelierService {

    private final HotelierRepository hotelierRepository;
    private final PlaceService placeService;
    private final CityService cityService;
    private final CategoryService categoryService;

    public HotelierServiceImpl(HotelierRepository hotelierRepository, PlaceService placeService,
                               CityService cityService, CategoryService categoryService) {
        this.hotelierRepository = hotelierRepository;
        this.placeService = placeService;
        this.cityService = cityService;
        this.categoryService = categoryService;
    }

    @Override
    public Hotelier save(String username, String password, String name, String surname, String email, String avatarURL) {
        //TODO: After user service is created
        return null;
    }

    @Override
    public List<Place> listAllManagedPlaces(Long managerId) {
        return this.placeService.listAllByManagerId(managerId);
    }

    @Override
    public Optional<Place> findPlace(Long managerId, Long placeId) {
        Optional<Hotelier> hotelier = this.hotelierRepository.findById(managerId);

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
    public Optional<Place> deletePlace(Long managerId, Long placeId) {
        Optional<Hotelier> hotelier = this.hotelierRepository.findById(managerId);

        if(hotelier.isPresent()){
            Optional<Place> managedPlace = this.findPlace(managerId, placeId);
            managedPlace.ifPresent(place -> hotelier.get().getManagedPlaces().remove(place.getPlaceId()));

            return managedPlace;
        } else {
            throw new HotelierNotFoundException(managerId);
        }
    }

    @Override
    public Place addPlace(Long managerId, Long cityId, String name, String description, String address, String telephoneNumber, Integer price, Long categoryId, List<String> gallery, String thumbnail, Coordinates map) {
        Optional<Hotelier> hotelier = this.hotelierRepository.findById(managerId);

        if(hotelier.isPresent()){
            Place place = this.placeService.save(managerId, cityId, name, description, address, telephoneNumber,
                    price, categoryId, gallery, thumbnail, map);
            hotelier.get().getManagedPlaces().add(place.getPlaceId());

            return place;
        } else {
            throw new HotelierNotFoundException(managerId);
        }
    }

    @Override
    public Place updatePlace(Long placeId, Long managerId, Long cityId, String name, String description,
                                       String address, String telephoneNumber, Integer price, Long categoryId,
                                       List<String> gallery, String thumbnail, Coordinates map) {

        Optional<Hotelier> hotelier = this.hotelierRepository.findById(managerId);

        if(hotelier.isPresent()){
            Place managedPlace = this.findPlace(managerId, placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

            this.categoryService
                    .findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
            managedPlace.setCategoryId(categoryId);

            this.cityService
                    .findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
            managedPlace.setCityId(cityId);

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
