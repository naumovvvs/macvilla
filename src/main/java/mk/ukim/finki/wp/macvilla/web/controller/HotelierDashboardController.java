package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Hotelier;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.HotelierNotFoundException;
import mk.ukim.finki.wp.macvilla.service.HotelierService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/dashboard"})
public class HotelierDashboardController {

    private final PlaceService placeService;
    private final HotelierService hotelierService;

    public HotelierDashboardController(PlaceService placeService, HotelierService hotelierService) {
        this.placeService = placeService;
        this.hotelierService = hotelierService;
    }

    @GetMapping(value = {"/hotelier/{id}"})
    public String getHotelierDashboardPage(@PathVariable(required = false) Long id, Model model){
        model.addAttribute("headTitle", "Hotelier dashboard");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "hotelier-profile.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "hotelier-profile");

        Hotelier hotelier = null;
        try {
            hotelier = (Hotelier) this.hotelierService.findById(id);
        } catch (HotelierNotFoundException exception) {
            return "redirect:/dashboard/hotelier?error=" + exception.getMessage();
        }


        List<Place> managedPlaces = this.placeService.listAllPlaces()
                .stream().filter(p -> p.getManager().getUserId().equals(id)).collect(Collectors.toList());
        model.addAttribute("hotelier", hotelier);
        model.addAttribute("managedPlaces", managedPlaces);
        return "master-template";
    }

    @GetMapping(value = {"/hotelier/{id}/remove-place/{placeId}"})
    public String removePlaceFromManaged(@PathVariable Long id, @PathVariable Long placeId) {
        Hotelier hotelier = null;
        try {
            hotelier = (Hotelier) this.hotelierService.findById(id);
        } catch (HotelierNotFoundException exception) {
            return "redirect:/dashboard/hotelier?error=" + exception.getMessage();
        }

        Optional<Place> place = this.placeService.findById(placeId);
        if(place.isPresent()){
            this.hotelierService.deletePlace(id, placeId);
            return "redirect:/dashboard/hotelier/" + id;
        } else {
            return "redirect:/dashboard/hotelier?error=No place found with the given id to remove!";
        }
    }

    @PostMapping("/hotelier/{id}/edit")
    public String updateHotelier(
            @PathVariable Long id,
            @RequestParam String name, @RequestParam String surname,
            @RequestParam String username, @RequestParam String password,
            @RequestParam String email, @RequestParam String thumbnail) {

        this.hotelierService.save(id, username, password, name, surname, email, thumbnail);
        return "redirect:/home";
    }
}
