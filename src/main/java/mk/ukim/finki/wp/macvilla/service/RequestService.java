package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Request;
import java.util.List;

public interface RequestService {
    List<Request> listAllRequestsByPlaceId(Long placeId);
    List<Request> listAllRequests();
}
