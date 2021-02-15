package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import mk.ukim.finki.wp.macvilla.model.exceptions.InvalidCategoryException;
import mk.ukim.finki.wp.macvilla.repository.AdministratorRepository;
import mk.ukim.finki.wp.macvilla.repository.RequestRepository;
import mk.ukim.finki.wp.macvilla.repository.UserRepository;
import mk.ukim.finki.wp.macvilla.service.AdministratorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

    /*@Override
    public List<User> listAllBlockedUsers() {
        return this.userRepository.findAllByBlockedIsTrue();
    }*/

    @Override
    public Optional<Request> removeFrom(String category, Request request) {
        if(category.toLowerCase(Locale.ROOT).equals("pending")){
            List<Request> pending = this.listAllPendingRequests();

            if(pending.isEmpty()){
                return Optional.empty();
            }else{
                pending.remove(request);
                // TODO: How to update the pending list? Just one administrator or many? For each admin separate list?
            }
        }else if(category.toLowerCase(Locale.ROOT).equals("approved")){
            List<Request> approved = this.listAllApprovedRequests();

            if(approved.isEmpty()){
                return Optional.empty();
            }else{
                approved.remove(request);
                // TODO: How to update the approved list? Just one administrator or many? For each admin separate list?
            }
        }else if(category.toLowerCase(Locale.ROOT).equals("denied")){
            List<Request> denied = this.listAllDeniedRequests();

            if(denied.isEmpty()){
                return Optional.empty();
            }else{
                denied.remove(request);
                // TODO: How to update the denied list? Just one administrator or many? For each admin separate list?
            }
        }else{
            throw new InvalidCategoryException(category);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Request> addTo(String category, Request request) {
        if(category.toLowerCase(Locale.ROOT).equals("pending")){
            List<Request> pending = this.listAllPendingRequests();

            if(pending.isEmpty()){
                return Optional.empty();
            }else{
                pending.add(request);

                // TODO: How to update the approved list? Just one administrator or many? For each admin separate list?
                //TODO How to add it, searching for adminId?
                return null;
            }
        }else if(category.toLowerCase(Locale.ROOT).equals("approved")){
            List<Request> approved = this.listAllApprovedRequests();

            if(approved.isEmpty()){
                return Optional.empty();
            }else{
                approved.add(request);
                // TODO: same as above
                return null;
            }
        }else if(category.toLowerCase(Locale.ROOT).equals("denied")){
            List<Request> denied = this.listAllDeniedRequests();

            if(denied.isEmpty()){
                return Optional.empty();
            }else{
                denied.add(request);
                // TODO: same as above
                return null;
            }
        }else{
            throw new InvalidCategoryException(category);
        }
    }
}
