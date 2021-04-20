package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.exceptions.PlaceNotFoundException;
import mk.ukim.finki.wp.macvilla.service.*;
import mk.ukim.finki.wp.macvilla.service.impl.FileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/place"})
public class PlaceController {

    private final PlaceService placeService;
    private final CityService cityService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final HotelierService hotelierService;
    private final ImageService imageService;
    private final RequestService requestService;
    private final ReviewService reviewService;
    private final ClientService clientService;

    private final FileService fileService;

    public PlaceController(PlaceService placeService, CityService cityService,
                           CategoryService categoryService, UserService userService,
                           HotelierService hotelierService, ImageService imageService,
                           RequestService requestService, ReviewService reviewService,
                           ClientService clientService, FileService fileService) {
        this.placeService = placeService;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.hotelierService = hotelierService;
        this.imageService = imageService;
        this.requestService = requestService;
        this.reviewService = reviewService;
        this.clientService = clientService;
        this.fileService = fileService;
    }

    @GetMapping(value = {"/{id}"})
    public String getPlacePage(@PathVariable Long id, Model model, HttpServletRequest request) {
        model.addAttribute("style1", "place.css");
        model.addAttribute("style2", "navbar.css");
        model.addAttribute("style3", "footer.css");

        model.addAttribute("headTitle", "Place");
        model.addAttribute("bodyContent", "place");

        Optional<User> optionalUser = this.userService.findByUsername(request.getRemoteUser());
        if (optionalUser.isPresent())
            model.addAttribute("clientId", optionalUser.get().getUserId());
        else
            model.addAttribute("clientId", "");

        Optional<Place> place = this.placeService.findById(id);
        if (place.isEmpty())
            return "redirect:/not-found";

        this.placeService.incrementVisits(place.get());
        model.addAttribute("placeId", place.get().getPlaceId());
        return "master-template";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOTELIER')")
    @GetMapping(value = {"/register"})
    public String getPlaceRegisterPage(HttpServletRequest request, Model model) {
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "place-register.css");
        model.addAttribute("style3", "footer.css");

        model.addAttribute("headTitle", "Place register");
        model.addAttribute("bodyContent", "place-register");

        List<City> cities = this.cityService.listAllCities();
        List<Category> categories = this.categoryService.listAllCategories();
        model.addAttribute("cities", cities);
        model.addAttribute("categories", categories);

        Optional<User> hotelier = this.userService.findByUsername(request.getRemoteUser());
        hotelier.ifPresent(user -> model.addAttribute("hotelier", user));

        return "master-template";
    }

    @PostMapping(value = {"/register"})
    public String placeRegister(@RequestParam String name, @RequestParam String description,
                                @RequestParam String telephoneNumber, @RequestParam String address,
                                @RequestParam Long categoryId, @RequestParam Long cityId,
                                @RequestParam MultipartFile[] gallery, @RequestParam MultipartFile thumbnail,
                                HttpServletRequest request) {

        Optional<User> user = this.userService.findByUsername(request.getRemoteUser());
        Hotelier manager = null;

        if (user.isPresent()) {
            manager = (Hotelier) hotelierService.findById(user.get().getUserId());

            Image thumbnailImage = this.imageService.save(FilepathConstants.IMAGE_DESTINATION_PREFIX + thumbnail.getOriginalFilename());
            fileService.uploadFile(thumbnail);

            List<Image> galleryList = new ArrayList<>();
            for (MultipartFile galleryImage : gallery) {
                this.fileService.uploadFile(galleryImage);
                galleryList.add(this.imageService.save(FilepathConstants.IMAGE_DESTINATION_PREFIX + galleryImage.getOriginalFilename()));
            }

            Request req = new Request(manager);
            this.requestService.addTo(req);

            this.placeService.save(manager.getUserId(), cityId, name, description, address, telephoneNumber,
                    categoryId, galleryList, thumbnailImage, req);

        } else {
            return "redirect:/not-found";
        }
        assert false;
        return "redirect:/dashboard/hotelier/" + manager.getUserId();
    }

    @PostMapping("/{id}/add-review")
    public String postReview(@PathVariable Long id,
                             @RequestParam String ratingStars, @RequestParam String reviewContent,
                             HttpServletRequest request) {
        Place place = this.placeService.findById(id).orElseThrow(() -> new PlaceNotFoundException(id));

        float rating = 1.0f;

        switch (ratingStars) {
            case "two_stars":
                rating = 2.0f;
                break;
            case "three_stars":
                rating = 3.0f;
                break;
            case "four_stars":
                rating = 4.0f;
                break;
            case "five_stars":
                rating = 5.0f;
                break;
        }

        Optional<User> client = this.userService.findByUsername(request.getRemoteUser());

        // if the user doesn't exist don't post the review
        if (client.isPresent()) {
            this.reviewService.create(reviewContent, rating, (Client) client.get(), place);
        }
        return "redirect:/place/" + place.getPlaceId();
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/{id}/add-fave/{clientId}")
    public String addToFavorites(@PathVariable Long id, @PathVariable Long clientId) {

        Client client = (Client) this.clientService.findById(clientId);
        Optional<Place> place = this.placeService.findById(id);

        if (place.isPresent())
            this.clientService.addToFavoritePlaces(client, place.get());
        else
            return "redirect:/not-found";

        return "redirect:/dashboard/client/" + clientId;
    }
}
