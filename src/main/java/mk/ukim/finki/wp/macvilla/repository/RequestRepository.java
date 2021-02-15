package mk.ukim.finki.wp.macvilla.repository;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.Request;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequestStatus(RequestStatus requestStatus);
    List<Request> findAllByPlace(Place place);
}
