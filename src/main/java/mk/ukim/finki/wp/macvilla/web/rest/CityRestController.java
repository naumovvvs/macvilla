package mk.ukim.finki.wp.macvilla.web.rest;

import mk.ukim.finki.wp.macvilla.model.City;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/city")
public class CityRestController {
    private final CityService cityService;

    public CityRestController(CityService cityService) {
        this.cityService = cityService;
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
    public List<Place> listAllPlacesInCity(@PathVariable Long id){
        return this.cityService.listAllPlacesInCity(id);
    }
}
