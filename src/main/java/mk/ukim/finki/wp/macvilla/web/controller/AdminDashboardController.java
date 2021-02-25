package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.exceptions.AdministratorNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.HotelierNotFoundException;
import mk.ukim.finki.wp.macvilla.service.AdministratorService;
import mk.ukim.finki.wp.macvilla.service.ClientService;
import mk.ukim.finki.wp.macvilla.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/dashboard"})
public class AdminDashboardController {

    private final AdministratorService administratorService;
    private final RequestService requestService;
    private final ClientService clientService;

    public AdminDashboardController(AdministratorService administratorService, RequestService requestService, ClientService clientService) {
        this.administratorService = administratorService;
        this.requestService = requestService;
        this.clientService = clientService;
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
}
