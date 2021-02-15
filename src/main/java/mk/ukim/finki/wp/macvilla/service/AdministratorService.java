package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.User;

import java.util.List;
import java.util.Optional;

public interface AdministratorService {
    List<User> listAllBlockedUsers();
}
