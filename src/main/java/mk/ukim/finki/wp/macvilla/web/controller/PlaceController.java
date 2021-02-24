package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/place"})
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping(value = {"/place/{id}"})
    public String getPlacePage(@PathVariable Long id, Model model){
        model.addAttribute("style1", "place.css");
        model.addAttribute("style2", "navbar.css");
        model.addAttribute("style3", "footer.css");

        model.addAttribute("headTitle", "Place");
        model.addAttribute("bodyContent", "place");

        Optional<Place> place = this.placeService.findById(id);


        return "master-template";
    }
}
