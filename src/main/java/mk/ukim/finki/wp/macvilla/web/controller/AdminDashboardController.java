package mk.ukim.finki.wp.macvilla.web.controller;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import mk.ukim.finki.wp.macvilla.model.exceptions.AdministratorNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.RequestNotFoundException;
import mk.ukim.finki.wp.macvilla.service.*;
import mk.ukim.finki.wp.macvilla.service.impl.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/dashboard"})
public class AdminDashboardController {

    private final AdministratorService administratorService;
    private final RequestService requestService;
    private final HotelierService hotelierService;
    private final PlaceService placeService;
    private final MessageService messageService;

    private final FileService fileService;

    public AdminDashboardController(AdministratorService administratorService, RequestService requestService,
                                    HotelierService hotelierService, PlaceService placeService,
                                    MessageService messageService, FileService fileService) {
        this.administratorService = administratorService;
        this.requestService = requestService;
        this.hotelierService = hotelierService;
        this.placeService = placeService;
        this.messageService = messageService;
        this.fileService = fileService;
    }

    @GetMapping(value = {"/admin/{id}"})
    public String getAdministratorDashboardPage(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("headTitle", "Administrator dashboard");
        model.addAttribute("style1", "navbar.css");
        model.addAttribute("style2", "admin-profile.css");
        model.addAttribute("style3", "footer.css");
        model.addAttribute("bodyContent", "admin-profile");

        Administrator administrator = null;
        try {
            administrator = (Administrator) this.administratorService.findById(id);
        } catch (AdministratorNotFoundException exception) {
            return "redirect:/not-found";
        }

        model.addAttribute("admin", administrator);

        List<Place> placeList = this.placeService.listAllPlaces();
        List<Place> pendingPlaces = placeList.stream()
                .filter(p -> p.getRequest().getRequestStatus().equals(RequestStatus.PENDING))
                .collect(Collectors.toList());
        List<Place> approvedPlaces = placeList.stream()
                .filter(p -> p.getRequest().getRequestStatus().equals(RequestStatus.APPROVED))
                .collect(Collectors.toList());
        List<Place> deniedPlaces = placeList.stream()
                .filter(p -> p.getRequest().getRequestStatus().equals(RequestStatus.DENIED))
                .collect(Collectors.toList());
        model.addAttribute("pendingPlaces", pendingPlaces);
        model.addAttribute("approvedPlaces", approvedPlaces);
        model.addAttribute("deniedPlaces", deniedPlaces);

        List<Message> messageList = this.messageService.findAll();
        model.addAttribute("messages", messageList);

        return "master-template";
    }

    @GetMapping(value = {"/admin/{id}/approve/{requestId}"})
    public String approveRequest(@PathVariable Long id, @PathVariable Long requestId) {

        Request request = null;
        try {
            request = this.requestService.findById(requestId);
        } catch (RequestNotFoundException exception) {
            return "redirect:/not-found";
        }
        this.requestService.changeStatus(request, RequestStatus.APPROVED);
        return "redirect:/dashboard/admin/" + id;
    }

    @GetMapping(value = {"/admin/{id}/deny/{requestId}"})
    public String denyRequest(@PathVariable Long id, @PathVariable Long requestId) {

        Request request = null;
        try {
            request = this.requestService.findById(requestId);
        } catch (RequestNotFoundException exception) {
            return "redirect:/not-found";
        }
        this.requestService.changeStatus(request, RequestStatus.DENIED);
        return "redirect:/dashboard/admin/" + id;
    }

    @PostMapping("/admin/{id}/edit")
    public String updateAdministrator(
            @PathVariable Long id,
            @RequestParam String name, @RequestParam String surname,
            @RequestParam String username, @RequestParam(required = false) String password,
            @RequestParam String email, @RequestParam(required = false) MultipartFile thumbnail) {

        if (thumbnail.getOriginalFilename() != null && !thumbnail.getOriginalFilename().isEmpty()) {
            this.fileService.uploadFile(thumbnail);
            this.administratorService.update(id, username, password, name, surname, email,
                    FilepathConstants.IMAGE_DESTINATION_PREFIX + thumbnail.getOriginalFilename());
        }
        this.administratorService.update(id, username, password, name, surname, email, "");
        return "redirect:/dashboard/admin/" + id;
    }
}
