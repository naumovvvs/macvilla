package mk.ukim.finki.wp.macvilla.web.rest;

import mk.ukim.finki.wp.macvilla.model.City;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.service.CityService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/city")
public class CityRestController {
    private final CityService cityService;
    private final PlaceService placeService;

    public CityRestController(CityService cityService, PlaceService placeService) {
        this.cityService = cityService;
        this.placeService = placeService;
    }

    @GetMapping()
    public List<City> getAllCities() {
        return this.cityService.listAllCities();
    }

    @GetMapping("/{id}/info")
    public City getCityById(@PathVariable Long id){
        if(this.cityService.findById(id).isPresent()){
            return this.cityService.findById(id).get();
        }else{
            return null;
        }
    }

    @GetMapping("/{id}/places")
    public List<Place> listAllPlacesInCity(@PathVariable Long id,
                                           @RequestParam(required = false) String value,
                                           @RequestParam(required = false) String how){
        // value can be 'name' or 'rating'
        // how can be 'asc' (ascending) OR 'desc' (descending)

        if(value!=null && !value.isEmpty() && how!=null && !how.isEmpty()) {
            boolean ascending = how.equals("asc");

            if(value.equals("rating")){
                if(ascending) {
                    return this.cityService.listAllPlacesInCity(id).stream().sorted(Comparator.comparing(Place::getRating)).collect(Collectors.toList());
                }else{
                    return this.cityService.listAllPlacesInCity(id).stream().sorted(Comparator.comparing(Place::getRating).reversed()).collect(Collectors.toList());
                }
            }else{
                if(ascending){
                    return this.cityService.listAllPlacesInCity(id).stream().sorted(Comparator.comparing(Place::getName)).collect(Collectors.toList());
                }else{
                    return this.cityService.listAllPlacesInCity(id).stream().sorted(Comparator.comparing(Place::getName).reversed()).collect(Collectors.toList());
                }
            }
        }

        return this.cityService.listAllPlacesInCity(id);
    }
}
