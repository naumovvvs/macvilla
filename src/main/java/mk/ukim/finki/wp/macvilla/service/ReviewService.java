package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Review;
import java.util.List;

public interface ReviewService {
    Review create(String content, Float rating, Long authorId, Long placeId);

    List<Review> listAllReviewsByPlaceId(Long placeId);

    List<Review> listAllReviewsByAuthorId(Long authorId);

    // if ascending = false, then we return places in descending order
    List<Review> listAllReviewsByRating(Float rating, boolean ascending);
}
