package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping(value = {"/city"})
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = {""})
    public String getCityPage(Model model){
        //List<Place> listPlaces = this.cityService.listAllPlacesInCity(id);

        //model.addAttribute("headTitle", this.cityService.findById(id).get().getName());
        model.addAttribute("headTitle", "TITLE");
        model.addAttribute("bodyContent", "city");
        model.addAttribute("style1", "city.css");
        model.addAttribute("style2", "navbar.css");
        model.addAttribute("style3", "footer.css");

        return "master-template";
    }
}
