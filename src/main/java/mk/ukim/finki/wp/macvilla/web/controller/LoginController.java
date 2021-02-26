package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/login"})
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(Model model){
        model.addAttribute("headTitle", "Login");
        model.addAttribute("bodyContent", "login");
        model.addAttribute("style1", "login.css");
        model.addAttribute("style2", "navbar.css");
        model.addAttribute("style3", "footer.css");

        return "master-template";
    }

    @PostMapping("")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, Model model){
        Optional<User> user = Optional.empty();

        System.out.println("VLEGUVA");

        try {
            user = this.userService.login(request.getParameter("username"), request.getParameter("password"));
        }catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("headTitle", "Login");
            model.addAttribute("style1", "login.css");
            model.addAttribute("style2", "navbar.css");
            model.addAttribute("style3", "footer.css");
            model.addAttribute("bodyContent", "login");

            return "master-template";
        }

        if(user.isPresent()) {
            model.addAttribute("headTitle", "Home");
            model.addAttribute("style1", "navbar.css");
            model.addAttribute("style2", "city-slider.css");
            model.addAttribute("style3", "footer.css");
            model.addAttribute("bodyContent", "home-page");

            request.getSession().setAttribute("user", user.get());

            return "master-template";
        }else{
            return "redirect:/login";
        }
    }
}
