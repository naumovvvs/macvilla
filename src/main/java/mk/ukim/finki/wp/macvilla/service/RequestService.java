package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Request;
import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<Request> listAllRequestsByPlaceId(Long placeId);
    List<Request> listAllRequests();
    List<Request> listAllPendingRequests();
    List<Request> listAllApprovedRequests();
    List<Request> listAllDeniedRequests();
    // removes request from a category
    Optional<Request> removeFrom(String category, Request request);
    // adds a request to a category
    Optional<Request> addTo(String category, Request request);
}
