package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.enums.Role;
import mk.ukim.finki.wp.macvilla.model.exceptions.*;
import mk.ukim.finki.wp.macvilla.repository.HotelierRepository;
import mk.ukim.finki.wp.macvilla.service.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public HotelierServiceImpl(HotelierRepository hotelierRepository, PlaceService placeService,
                               CityService cityService, CategoryService categoryService,
                               PasswordEncoder passwordEncoder) {
        this.hotelierRepository = hotelierRepository;
        this.placeService = placeService;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Place> listAllManagedPlaces(Long managerId) {
        return this.placeService.listAllByManagerId(managerId);
    }

    @Override
    public Optional<Place> findPlace(Long managerId, Long placeId) {
        Hotelier hotelier = (Hotelier) this.hotelierRepository
                .findById(managerId).orElseThrow(() -> new HotelierNotFoundException(managerId));

        return Optional.of(this.placeService
                .listAllByManagerId(hotelier.getUserId())
                .stream()
                .filter(p -> p.getPlaceId().equals(placeId)).collect(Collectors.toList()).get(0));
    }

    @Override
    public User findById(Long managerId) {
        return this.hotelierRepository
                .findById(managerId).filter(h -> h.getRole().equals(Role.ROLE_HOTELIER))
                .orElseThrow(() -> new HotelierNotFoundException(managerId));
    }

    @Override
    public User deleteById(Long managerId) {
        User user = this.hotelierRepository.findById(managerId)
                .orElseThrow(() -> new HotelierNotFoundException(managerId));
        this.hotelierRepository.deleteById(managerId);
        return user;
    }

    @Override
    public Hotelier unblock(Long managerId) {
        Hotelier hotelier = (Hotelier) this.hotelierRepository.findById(managerId)
                .orElseThrow(() -> new HotelierNotFoundException(managerId));
        hotelier.setBlocked(false);
        this.hotelierRepository.save(hotelier);
        return hotelier;
    }

    @Override
    public List<User> findAllBlocked() {
        return this.hotelierRepository.findAll().stream().filter(User::isBlocked)
                .collect(Collectors.toList());
    }

    @Override
    public Hotelier update(Long id, String username, String password, String name, String surname,
                           String email, String avatarURL) {
        // check only for avatar, the other params are required
        if (avatarURL == null || avatarURL.isEmpty()) {
            avatarURL = "";
        }

        Hotelier hotelier = (Hotelier) this.hotelierRepository
                .findById(id).orElseThrow(() -> new HotelierNotFoundException(id));

        if (!passwordEncoder.matches(password, hotelier.getPassword()) && !password.isEmpty()) {
            hotelier.setPassword(passwordEncoder.encode(password));
        }

        hotelier.setUsername(username);
        hotelier.setName(name);
        hotelier.setSurname(surname);
        hotelier.setEmail(email);
        hotelier.setAvatarURL(avatarURL);

        return this.hotelierRepository.save(hotelier);
    }

    @Override
    public Optional<Place> deletePlace(Long managerId, Long placeId) {
        Hotelier hotelier = (Hotelier) this.hotelierRepository
                .findById(managerId).orElseThrow(() -> new HotelierNotFoundException(managerId));

        Optional<Place> managedPlace = this.findPlace(hotelier.getUserId(), placeId);

        if (managedPlace.isPresent()) {
            return Optional.of(this.placeService.removeById(managedPlace.get().getPlaceId()));
        } else {
            throw new PlaceNotFoundException(placeId);
        }
    }

    @Override
    public Place addPlace(Long managerId, Long cityId, String name, String description, String address,
                          String telephoneNumber, Integer price, Long categoryId, List<Image> gallery,
                          Image thumbnail) {
        this.hotelierRepository
                .findById(managerId).orElseThrow(() -> new HotelierNotFoundException(managerId));

        this.cityService.findById(cityId).
                orElseThrow(() -> new CityNotFoundException(cityId));

        this.categoryService.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        return this.placeService.save(managerId, cityId, name, description, address, telephoneNumber,
                categoryId, gallery, thumbnail);
    }

    @Override
    public Place updatePlace(Long placeId, Long managerId, Long cityId, String name, String description,
                             String address, String telephoneNumber, Integer price, Long categoryId,
                             List<Image> gallery, Image thumbnail) {

        Hotelier hotelier = (Hotelier) this.hotelierRepository
                .findById(managerId).orElseThrow(() -> new HotelierNotFoundException(managerId));

        Place managedPlace = this.findPlace(hotelier.getUserId(), placeId)
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
        managedPlace.setGallery(gallery);
        managedPlace.setThumbnail(thumbnail);
//      managedPlace.setMap(map);

        return managedPlace;
    }
}
