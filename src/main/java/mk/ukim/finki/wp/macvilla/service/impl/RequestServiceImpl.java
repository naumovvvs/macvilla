package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import mk.ukim.finki.wp.macvilla.model.exceptions.RequestNotFoundException;
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
    public List<Request> listAllRequestsByPlaceId(Place place) {
        return this.requestRepository.findAllByPlace(place);
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
    public Optional<Request> removeFrom(Request request) {
        this.requestRepository.delete(request);
        return Optional.of(request);
    }

    @Override
    public Optional<Request> addTo(Request request) {
        this.requestRepository.save(request);
        return Optional.of(request);
    }

    @Override
    public Request findById(Long id) {
        return this.requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException(id));
    }
}
