package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Hotelier;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.exceptions.HotelierNotFoundException;
import mk.ukim.finki.wp.macvilla.service.*;
import mk.ukim.finki.wp.macvilla.service.impl.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/dashboard"})
public class HotelierDashboardController {

    private final PlaceService placeService;
    private final HotelierService hotelierService;
    private final ReviewService reviewService;
    private final RequestService requestService;
    private final ImageService imageService;
    private final FileService fileService;

    public HotelierDashboardController(PlaceService placeService, HotelierService hotelierService,
                                       ReviewService reviewService, RequestService requestService,
                                       ImageService imageService, FileService fileService) {
        this.placeService = placeService;
        this.hotelierService = hotelierService;
        this.reviewService = reviewService;
        this.requestService = requestService;
        this.imageService = imageService;
        this.fileService = fileService;
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
            return "redirect:/not-found";
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
            return "redirect:/not-found";
        }

        Optional<Place> place = this.placeService.findById(placeId);

        if(place.isPresent()){
            this.reviewService.removeAllWithPlace(place.get());
            this.hotelierService.deletePlace(id, placeId);
            this.requestService.removeById(place.get().getRequest().getRequestId());
            this.imageService.removeById(place.get().getThumbnail().getImageId());
            return "redirect:/dashboard/hotelier/" + id;
        } else {
            return "redirect:/not-found";
        }
    }

    @PostMapping("/hotelier/{id}/edit")
    public String updateHotelier(
            @PathVariable Long id,
            @RequestParam String name, @RequestParam String surname,
            @RequestParam String username, @RequestParam(required = false) String password,
            @RequestParam String email, @RequestParam(required = false) MultipartFile thumbnail) {

        if (thumbnail.getOriginalFilename() != null && !thumbnail.getOriginalFilename().isEmpty()) {
            this.fileService.uploadFile(thumbnail);
            this.hotelierService.update(id, username, password, name, surname, email,
                    FilepathConstants.IMAGE_DESTINATION_PREFIX + thumbnail.getOriginalFilename());
        }
        this.hotelierService.update(id, username, password, name, surname, email, "");
        return "redirect:/dashboard/hotelier/" + id;
    }
}
