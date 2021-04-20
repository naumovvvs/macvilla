package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.ReviewService;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final UserService userService;
    private final ReviewService reviewService;

    public HomeController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping(value = {"/", "/home"})
    public String getHomePage(HttpServletRequest request, Model model) {
        // TODO: create master-template AND pass title of page in head tag
        model.addAttribute("headTitle", "Home");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "city-slider.css");
        model.addAttribute("style3", "reviews.css");
        model.addAttribute("style4", "footer.css");
        model.addAttribute("style5", "wizard.css");
        model.addAttribute("bodyContent", "home-page");
        // latest 6 reviews for showing in carousel
        model.addAttribute("reviews", this.reviewService.getLatestReviews());

        Optional<User> optionalUser = this.userService.findByUsername(request.getRemoteUser());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String roleEnum = user.getRole().name();
            Long id = user.getUserId();
            String role;

            if (roleEnum.equals("ROLE_CLIENT")) {
                role = "client";
            } else if (roleEnum.equals("ROLE_HOTELIER")) {
                role = "hotelier";
            } else {
                role = "admin";
            }

            model.addAttribute("userId", "/dashboard/" + role + "/" + id);
        } else {
            model.addAttribute("userId", "/login");
        }
        return "master-template";
    }

    @GetMapping(value = {"/about-us"})
    public String getAboutUsPage(Model model) {
        model.addAttribute("headTitle", "About Us");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "about-us.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "about-us");
        return "master-template";
    }

    @GetMapping(value = {"/contact"})
    public String getContactPage(@RequestParam(required = false) String sentMessage, Model model,
                                 HttpServletRequest request) {
        model.addAttribute("headTitle", "Contact");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "contact.css");
        model.addAttribute("style3", "footer-light.css");
        model.addAttribute("bodyContent", "contact");

        Optional<User> optionalUser = this.userService.findByUsername(request.getRemoteUser());
        optionalUser.ifPresent(user -> model.addAttribute("user", user));

        if (sentMessage != null && !sentMessage.isEmpty()) {
            model.addAttribute("sentMessage", true);
        }

        return "master-template";
    }

    @GetMapping("/not-found")
    public String getNotFoundPage(Model model) {
        model.addAttribute("headTitle", "Not Found - 404");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "not-found.css");
        model.addAttribute("style3", "footer-light.css");
        model.addAttribute("bodyContent", "not-found");
        return "master-template";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("headTitle", "Access Denied - 403");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "access-denied.css");
        model.addAttribute("style3", "footer-light.css");
        model.addAttribute("bodyContent", "access-denied");
        return "master-template";
    }
}
