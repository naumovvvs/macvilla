package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.User;

import java.util.List;
import java.util.Optional;

public interface AdministratorService {
    List<Request> listAllPendingRequests();
    List<Request> listAllApprovedRequests();
    List<Request> listAllDeniedRequests();
    List<User> listAllBlockedUsers();
    // removes request from a category
    Optional<Request> removeFrom(String category, Request request);
    // adds a request to a category
    Optional<Request> addTo(String category, Request request);
}
