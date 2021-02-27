package mk.ukim.finki.wp.macvilla.web.rest;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.AdministratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AdministratorService administratorService;

    public AdminRestController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @GetMapping
    public List<User> listAllAdministrators(){
        return this.administratorService.listAll();
    }

    @GetMapping("/{id}")
    public User getAdministrator(@PathVariable Long id){
        return this.administratorService.findById(id);
    }
}
