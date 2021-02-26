package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home"})
    public String getHomePage(HttpServletRequest request, Model model){
        // TODO: create master-template AND pass title of page in head tag
        model.addAttribute("headTitle", "Home");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "city-slider.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "home-page");

        Optional<User> optionalUser = this.userService.findByUsername(request.getRemoteUser());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String roleEnum = user.getRole().name();
            Long id = user.getUserId();
            String role;

            if(roleEnum.equals("ROLE_CLIENT")){
                role = "client";
            }else if(roleEnum.equals("ROLE_HOTELIER")) {
                role = "hotelier";
            }else {
                role = "admin";
            }

            model.addAttribute("userId", "/dashboard/" + role +"/" + id);
        }else{
            model.addAttribute("userId", "/login");
        }
        return "master-template";
    }

    @GetMapping(value = {"/about-us"})
    public String getAboutUsPage(Model model){
        model.addAttribute("headTitle", "About Us");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "about-us.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "about-us");
        return "master-template";
    }

    @GetMapping(value = {"/contact"})
    public String getContactPage(Model model){
        model.addAttribute("headTitle", "Contact");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "contact.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "contact");
        return "master-template";
    }

    @GetMapping("access_denied")
    public String getAccessDeniedPage(Model model){
        // TODO: make access_denied page
        model.addAttribute("headTitle", "Access Denied");
        model.addAttribute("bodyContent", "accessDenied");
        return "master-template";
    }
}
