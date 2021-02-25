package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    private List<User> users;

    public ExampleController() {
        this.users = new ArrayList<>();
        this.users.add(new User("user1", "pas1", "Name1", "Surname1", "Email1", "URL1"));
        this.users.add(new User("user2", "pas2", "Name2", "Surname2", "Email2", "URL2"));
        this.users.add(new User("user3", "pas3", "Name3", "Surname3", "Email3", "URL3"));
        this.users.add(new User("user4", "pas4", "Name4", "Surname4", "Email4", "URL4"));
    }

    @GetMapping("")
    public List<User> getUsers(){
        return this.users;
    }
}
