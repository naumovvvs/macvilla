package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/register"})
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("error", error);
        }

        model.addAttribute("style1", "register.css");
        model.addAttribute("style2", "navbar.css");
        model.addAttribute("style3", "footer-light.css");
        model.addAttribute("headTitle", "Register");
        model.addAttribute("bodyContent", "register");

        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String name, @RequestParam String surname,
                           @RequestParam String email, @RequestParam(required = false) String address,
                           @RequestParam(required = false) String bdate, @RequestParam String role,
                           @RequestParam(required = false) String avatarURL){

        if(avatarURL==null || avatarURL.isEmpty()){
            avatarURL = "";
        }

        try {
            if(role.equals("ROLE_CLIENT")) {
                this.userService.register(username, password, name, surname, email, avatarURL, bdate, address, role);
            }else{
                this.userService.register(username, password, name, surname, email, avatarURL, "/", "/", role);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return "redirect:/register?error="+ex.getMessage();
        }

        return "redirect:/login";
    }

    @PostMapping("/social/fb")
    public String registerSocialFB(@RequestBody User user){
        this.userService.registerSocial(user);

        return "redirect:/login";
    }

}
