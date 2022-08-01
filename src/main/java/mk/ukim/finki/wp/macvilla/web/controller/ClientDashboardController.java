package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.ClientNotFoundException;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import mk.ukim.finki.wp.macvilla.service.impl.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping(value = {"/dashboard"})
public class ClientDashboardController {

    private final ClientService clientService;
    private final PlaceService placeService;
    private final FileService fileService;

    public ClientDashboardController(ClientService clientService, PlaceService placeService, FileService fileService) {
        this.clientService = clientService;
        this.placeService = placeService;
        this.fileService = fileService;
    }

    @GetMapping(value = {"/client/{id}"})
    public String getClientDashboardPage(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("headTitle", "Client dashboard");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "client-profile.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "client-profile");
        model.addAttribute("userId", "/dashboard/client/"+id);

        Client client = null;
        try {
            client = (Client) this.clientService.findById(id);
        } catch (ClientNotFoundException exception) {
            return "redirect:/not-found";
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
            return "redirect:/not-found";
        }

        Optional<Place> place = this.placeService.findById(placeId);
        if(place.isPresent()){
            this.clientService.removeFromFavoritePlaces(client, place.get());
            return "redirect:/dashboard/client/" + id;
        } else {
            return "redirect:/not-found";
        }
    }

    @PostMapping("/client/{id}/edit")
    public String updateClient(
            @PathVariable Long id,
            @RequestParam String name, @RequestParam String surname,
            @RequestParam String username, @RequestParam(required = false) String password,
            @RequestParam String city, @RequestParam String email,
            @RequestParam(required = false) MultipartFile thumbnail, @RequestParam String birthDate) {

        if (thumbnail.getOriginalFilename() != null && !thumbnail.getOriginalFilename().isEmpty()) {
            this.fileService.uploadFile(thumbnail);
            this.clientService.update(id, username, password, name, surname, email,
                    FilepathConstants.IMAGE_DESTINATION_PREFIX + thumbnail.getOriginalFilename(), birthDate, city);
        }
        this.clientService.update(id, username, password, name, surname, email, "", birthDate, city);
        return "redirect:/dashboard/client/" + id;
    }
}
