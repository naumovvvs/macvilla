package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import mk.ukim.finki.wp.macvilla.repository.RequestRepository;
import mk.ukim.finki.wp.macvilla.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> listAllRequestsByPlaceId(Long placeId) {
        // TODO one request is for one place, maybe findRequestByPlaceId ?
        return null;
    }

    @Override
    public List<Request> listAllRequests() {
        return this.requestRepository.findAll();
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
    public Optional<Request> removeFrom(String category, Request request) {
        if(category.toLowerCase(Locale.ROOT).equals("pending")){

        }else if(category.toLowerCase(Locale.ROOT).equals("approved"){

        }else if(category.toLowerCase(Locale.ROOT).equals("denied"){

        }else{

        }
    }

    @Override
    public Optional<Request> addTo(String category, Request request) {
        if(category.toLowerCase(Locale.ROOT).equals("pending")){

        }else if(category.toLowerCase(Locale.ROOT).equals("approved"){

        }else if(category.toLowerCase(Locale.ROOT).equals("denied"){

        }else{

        }
    }
}
