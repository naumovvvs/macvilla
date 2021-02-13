package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.repository.PlaceRepository;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public List<Place> listAllPlaces() {
        return this.placeRepository.findAll();
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
