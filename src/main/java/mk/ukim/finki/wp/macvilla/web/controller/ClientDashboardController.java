package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.ClientNotFoundException;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
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
            return "redirect:/dashboard/client?error=" + exception.getMessage();
        }

        List<Place> placeList = this.placeService.listAllPlaces();
        client.setFavoritePlaces(placeList);
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
            return "redirect:/dashboard/client?error=No place found with the given id to remove!";
        }
    }
}
