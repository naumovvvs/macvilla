package mk.ukim.finki.wp.macvilla.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc")
public class ExampleControllerMVC {

    @GetMapping
    public String getExamplePage(Model model){
        model.addAttribute("headTitle", "Place");
        model.addAttribute("bodyContent", "example");

        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "footer.css");

        return "master-template";
    }
}
