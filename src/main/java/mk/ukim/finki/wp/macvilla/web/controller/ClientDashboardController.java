package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.ClientNotFoundException;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/dashboard"})
public class ClientDashboardController {

    private final ClientService clientService;
    private final PlaceService placeService;

    public ClientDashboardController(ClientService clientService, PlaceService placeService) {
        this.clientService = clientService;
        this.placeService = placeService;
    }

    @GetMapping(value = {"/client/{id}"})
    public String getClientDashboardPage(@PathVariable(name = "id", required = false) Long id, Model model) {
        model.addAttribute("headTitle", "Client dashboard");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "client-profile.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "client-profile");

        Client client = null;
        try {
            client = (Client) this.clientService.findById(id);
        } catch (ClientNotFoundException exception) {
            return "redirect:/dashboard/client/" + id + "?error=" + exception.getMessage();
        }
        model.addAttribute("client", client);

        return "master-template";
    }

    @GetMapping(value = {"/client/{id}/remove-fave/{placeId}"})
    public String removePlaceFromFavorites(@PathVariable Long id, @PathVariable Long placeId) {
        Client client = null;
        try {
            client = (Client) this.clientService.findById(id);
        } catch (ClientNotFoundException exception) {
            return "redirect:/dashboard/client?error=" + exception.getMessage();
        }

        Optional<Place> place = this.placeService.findById(placeId);
        if(place.isPresent()){
            this.clientService.removeFromFavoritePlaces(client, place.get());
            return "redirect:/dashboard/client/" + id;
        } else {
            return "redirect:/dashboard/client/" + id + "?error=No place found with the given id to remove!";
        }
    }

    @PostMapping("/client/{id}/edit")
    public String updateClient(
            @PathVariable Long id,
            @RequestParam String name, @RequestParam String surname,
            @RequestParam String username,  @RequestParam String password,
            @RequestParam String city, @RequestParam String email,
            @RequestParam String thumbnail, @RequestParam String birthDate) {

        this.clientService.update(id, username, password, name, surname, email, thumbnail, birthDate, city);
        return "redirect:/dashboard/client/" + id;
    }
}
