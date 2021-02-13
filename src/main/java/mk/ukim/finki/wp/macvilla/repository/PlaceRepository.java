package mk.ukim.finki.wp.macvilla.repository;

import mk.ukim.finki.wp.macvilla.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByCityId(Long cityId);
    List<Place> findAllByCategoryId(Long categoryId);
    List<Place> findAllByManagerId(Long managerId);
    List<Place> findAllByRatingGreaterThanEqual(Float rating);
}
