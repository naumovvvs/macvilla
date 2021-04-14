package mk.ukim.finki.wp.macvilla.web.rest;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/social")
public class SocialLoginController {
    private final UserService userService;

    public SocialLoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/fbRegister")
    @ResponseBody
    public RedirectView fbRegister(@RequestBody User user) { // this is for JSON parameter
        this.userService.registerSocial(user);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/login");

        return redirectView;
    }
}
