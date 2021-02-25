package mk.ukim.finki.wp.macvilla.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/dashboard"})
public class HotelierDashboardController {

    @GetMapping(value = {"/hotelier/{id}"})
    public String getHotelierDashboardPage(@PathVariable(required = false) Long id, Model model){
        model.addAttribute("headTitle", "Hotelier dashboard");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "hotelier-profile.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "hotelier-profile");
        return "master-template";
    }
}
