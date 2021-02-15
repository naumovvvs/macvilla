package mk.ukim.finki.wp.macvilla.repository;

import mk.ukim.finki.wp.macvilla.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByCity(City city);
    List<Place> findAllByCategory(Category categoryId);
    List<Place> findAllByManager(User managerId);
    List<Place> findAllByRatingGreaterThanEqual(Float rating);
}
