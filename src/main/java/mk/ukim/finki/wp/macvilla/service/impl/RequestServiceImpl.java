package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.repository.RequestRepository;
import mk.ukim.finki.wp.macvilla.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
