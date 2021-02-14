package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import mk.ukim.finki.wp.macvilla.repository.AdministratorRepository;
import mk.ukim.finki.wp.macvilla.repository.RequestRepository;
import mk.ukim.finki.wp.macvilla.repository.UserRepository;
import mk.ukim.finki.wp.macvilla.service.AdministratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public AdministratorServiceImpl(AdministratorRepository administratorRepository, RequestRepository requestRepository, UserRepository userRepository) {
        this.administratorRepository = administratorRepository;
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Request> listAllPendingRequests() {
        return this.requestRepository.findAllByRequestStatus(RequestStatus.PENDING);
    }

    @Override
    public List<Request> listAllApprovedRequests() {
        return this.requestRepository.findAllByRequestStatus(RequestStatus.APPROVED);
    }

    @Override
    public List<Request> listAllDeniedRequests() {
        return this.requestRepository.findAllByRequestStatus(RequestStatus.DENIED);
    }

    @Override
    public List<User> listAllBlockedUsers() {
        return this.userRepository.findAllByBlockedIsTrue();
    }
}
