package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    public PlaceController(PlaceService placeService, CityService cityService,
                           CategoryService categoryService, UserService userService,
                           HotelierService hotelierService, ImageService imageService) {
        this.placeService = placeService;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.hotelierService = hotelierService;
        this.imageService = imageService;
    }

    @GetMapping(value = {"/{id}"})
    public String getPlacePage(@PathVariable Long id, Model model){
        model.addAttribute("style1", "place.css");
        model.addAttribute("style2", "navbar.css");
        model.addAttribute("style3", "footer.css");

        model.addAttribute("headTitle", "Place");
        model.addAttribute("bodyContent", "place");

        return "master-template";
    }

    @GetMapping(value = {"/register"})
    public String getPlaceRegisterPage(HttpServletRequest request, Model model){
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
                                @RequestParam String latitude, @RequestParam String longitude,
                                @RequestParam String gallery, @RequestParam String thumbnail,
                                HttpServletRequest request){

        Optional<User> user = this.userService.findByUsername(request.getRemoteUser());
        Hotelier manager = null;

        if(user.isPresent()){
            manager = (Hotelier) hotelierService.findById(user.get().getUserId());
            Image thumbnailImage = this.imageService.save(thumbnail);

            String [] galleryArray = gallery.split("\\s+");
            List<Image> galleryList = new ArrayList<>();
            for(String galleryImage : galleryArray){
                galleryList.add(this.imageService.save(galleryImage));
            }

            this.placeService.save(manager.getUserId(), cityId, name, description, address, telephoneNumber,
                    categoryId, galleryList, thumbnailImage);

        } else {
            //error
        }
        assert false;
        return "redirect:/dashboard/hotelier/" + manager.getUserId();
    }
}
