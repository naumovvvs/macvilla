package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Message;
import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> findAll();
    Optional<Message> findById(Long id);
    Optional<Message> save(String userName, String userSurname, String userEmail, String content);
    void delete(Long id);
}
