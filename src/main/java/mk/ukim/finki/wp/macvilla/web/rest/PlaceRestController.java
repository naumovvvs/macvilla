package mk.ukim.finki.wp.macvilla.web.rest;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.PlaceNotFoundException;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/place")
public class PlaceRestController {

    private final PlaceService placeService;

    public PlaceRestController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/{id}")
    public Place getPlace(@PathVariable Long id){
        return this.placeService.findById(id).orElseThrow(() -> new PlaceNotFoundException(id));
    }
}
