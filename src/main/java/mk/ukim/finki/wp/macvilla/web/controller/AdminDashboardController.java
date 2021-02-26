package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import mk.ukim.finki.wp.macvilla.model.exceptions.AdministratorNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.ClientNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.RequestNotFoundException;
import mk.ukim.finki.wp.macvilla.service.AdministratorService;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import mk.ukim.finki.wp.macvilla.service.RequestService;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value = {"/dashboard"})
public class AdminDashboardController {

    private final AdministratorService administratorService;
    private final RequestService requestService;
    private final ClientService clientService;
    private final UserService userService;

    public AdminDashboardController(AdministratorService administratorService, RequestService requestService,
                                    ClientService clientService, UserService userService) {
        this.administratorService = administratorService;
        this.requestService = requestService;
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping(value = {"/admin/{id}"})
    public String getAdministratorDashboardPage(@PathVariable(required = false) Long id, Model model){
        model.addAttribute("headTitle", "Administrator dashboard");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "admin-profile.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "admin-profile");

        Administrator administrator = null;
        try {
            administrator = (Administrator) this.administratorService.findById(id);
        } catch (AdministratorNotFoundException exception) {
            return "redirect:/dashboard/admin?error=" + exception.getMessage();
        }

        model.addAttribute("admin", administrator);

        List<Request> pendingRequests = this.requestService.listAllPendingRequests();
        List<Request> approvedRequests = this.requestService.listAllApprovedRequests();
        List<Request> deniedRequests = this.requestService.listAllDeniedRequests();
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("approvedRequests", approvedRequests);
        model.addAttribute("deniedRequests", deniedRequests);

        List<User> blockedUsers = this.clientService.findAllBlockedUsers();
        model.addAttribute("blockedUsers", blockedUsers);
        return "master-template";
    }

    @GetMapping(value = {"/admin/{id}/approve/{requestId}"})
    public String approveRequest(@PathVariable Long id, @PathVariable Long requestId) {

        Request request = null;
        try{
            request = this.requestService.findById(requestId);
        } catch (RequestNotFoundException exception) {
            return "redirect:/dashboard/admin?error=No request found with the given id!";
        }
        this.requestService.removeFrom(request);
        request.setRequestStatus(RequestStatus.APPROVED);
        this.requestService.addTo(request);

        return "redirect:/dashboard/admin/" + id;
    }

    @GetMapping(value = {"/admin/{id}/deny/{requestId}"})
    public String denyRequest(@PathVariable Long id, @PathVariable Long requestId) {

        Request request = null;
        try{
            request = this.requestService.findById(requestId);
        } catch (RequestNotFoundException exception) {
            return "redirect:/dashboard/admin?error=No request found with the given id!";
        }
        this.requestService.removeFrom(request);
        request.setRequestStatus(RequestStatus.DENIED);
        this.requestService.addTo(request);

        return "redirect:/dashboard/admin/" + id;
    }

    @GetMapping(value = {"/admin/{id}/remove/{requestId}"})
    public String removeRequest(@PathVariable Long id, @PathVariable Long requestId) {

        Request request = null;
        try{
            request = this.requestService.findById(requestId);
        } catch (RequestNotFoundException exception) {
            return "redirect:/dashboard/admin?error=No request found with the given id!";
        }
        this.requestService.removeFrom(request);
        return "redirect:/dashboard/admin/" + id;
    }

    @PostMapping("/admin/{id}/edit")
    public String updateAdministrator(
            @PathVariable Long id,
            @RequestParam String name, @RequestParam String surname,
            @RequestParam String username, @RequestParam String password,
            @RequestParam String email, @RequestParam String thumbnail) {

        this.administratorService.save(id, username, password, name, surname, email, thumbnail);
        return "redirect:/home";
    }

//    @GetMapping(value = {"/admin/{id}/remove/{userId}"})
//    public String unblockUser(@PathVariable Long id, @PathVariable Long userId) {
//
//        User client = null;
//        try{
//            client = this.clientService.findById(userId);
//        } catch (ClientNotFoundException exception) {
//            return "redirect:/dashboard/admin?error=No client found with the given id!";
//        }
//        client.setBlocked(false);
//
//        return "redirect:/dashboard/admin/" + id;
//    }
}
