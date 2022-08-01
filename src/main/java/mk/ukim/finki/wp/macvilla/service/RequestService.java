package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<Request> listAllRequestsByPlaceId(Place place);
    List<Request> listAllRequests();
    List<Request> listAllPendingRequests();
    List<Request> listAllApprovedRequests();
    List<Request> listAllDeniedRequests();
    // removes request
    Optional<Request> removeFrom(Request request);
    // adds a request
    Optional<Request> addTo(Request request);
    Request changeStatus(Request request, RequestStatus requestStatus);
    Request findById(Long id);
    void removeById(Long id);
}
