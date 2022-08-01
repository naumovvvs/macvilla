package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.Image;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.CityService;
import mk.ukim.finki.wp.macvilla.service.ImageService;
import mk.ukim.finki.wp.macvilla.service.UserService;
import mk.ukim.finki.wp.macvilla.service.impl.FileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;
    private final UserService userService;
    private final ImageService imageService;
    private final FileService fileService;

    public CityController(CityService cityService, UserService userService,
                          ImageService imageService, FileService fileService) {
        this.cityService = cityService;
        this.userService = userService;
        this.imageService = imageService;
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public String getCityPage(@PathVariable Long id, Model model) {
        //List<Place> listPlaces = this.cityService.listAllPlacesInCity(id);

        //model.addAttribute("headTitle", this.cityService.findById(id).get().getName());
        model.addAttribute("headTitle", "City");
        model.addAttribute("bodyContent", "city");
        model.addAttribute("style1", "city.css");
        model.addAttribute("style2", "navbar.css");
        model.addAttribute("style3", "footer.css");

        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String getAddCityPage(Model model, HttpServletRequest request) {
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "add-city.css");
        model.addAttribute("style3", "footer.css");

        model.addAttribute("headTitle", "Add city");
        model.addAttribute("bodyContent", "add-city");

        Optional<User> administrator = this.userService.findByUsername(request.getRemoteUser());
        administrator.ifPresent(user -> model.addAttribute("admin", user));

        return "master-template";
    }

    @PostMapping("/add")
    public String addCity(@RequestParam String name, @RequestParam String description,
                          @RequestParam MultipartFile thumbnail) {

        Image image = this.imageService.save(FilepathConstants.IMAGE_DESTINATION_PREFIX + thumbnail.getOriginalFilename());
        fileService.uploadFile(thumbnail);

        this.cityService.save(name, description, image);
        return "redirect:/#city-slider";
    }
}