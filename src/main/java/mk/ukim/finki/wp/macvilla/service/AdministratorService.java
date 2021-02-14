package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.User;

import java.util.List;

public interface AdministratorService {
    List<Request> listAllPendingRequests();
    List<Request> listAllApprovedRequests();
    List<Request> listAllDeniedRequests();
    List<User> listAllBlockedUsers();
}
