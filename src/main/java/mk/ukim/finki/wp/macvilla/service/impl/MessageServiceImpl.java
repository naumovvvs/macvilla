package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Message;
import mk.ukim.finki.wp.macvilla.repository.MessageRepository;
import mk.ukim.finki.wp.macvilla.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAll() {
        return this.messageRepository.findAll();
    }

    @Override
    public Optional<Message> findById(Long id) {
        return this.messageRepository.findById(id);
    }

    @Override
    public Optional<Message> save(String userName, String userSurname, String userEmail, String content) {
        Message message = new Message(userName, userSurname, userEmail, content);
        return Optional.of(this.messageRepository.save(message));
    }

    @Override
    public void delete(Long id) {
        this.messageRepository.deleteById(id);
    }
}
