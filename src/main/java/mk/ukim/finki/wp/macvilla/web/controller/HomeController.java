package mk.ukim.finki.wp.macvilla.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String getHomePage(Model model){
        // TODO: create master-template AND pass title of page in head tag
        model.addAttribute("headTitle", "Home");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "city-slider.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "home-page");
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