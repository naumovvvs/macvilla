package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.service.MessageService;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String saveMessage(@RequestParam String txtName, @RequestParam String txtSurname,
                              @RequestParam String txtEmail, @RequestParam String txtMsg) {
        this.messageService.save(txtName, txtSurname, txtEmail, txtMsg);
        return "redirect:/contact";
    }

    @GetMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Long id, HttpServletRequest request) {
        this.messageService.delete(id);

        Optional<User> optionalUser = this.userService.findByUsername(request.getRemoteUser());
        return optionalUser.map(user -> "redirect:/dashboard/admin/" + user.getUserId())
                .orElse("redirect:/not-found");
    }

}
