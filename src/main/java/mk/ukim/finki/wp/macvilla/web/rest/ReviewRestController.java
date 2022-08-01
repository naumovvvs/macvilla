package mk.ukim.finki.wp.macvilla.web.rest;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.Review;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.model.exceptions.PlaceNotFoundException;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import mk.ukim.finki.wp.macvilla.service.ReviewService;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews(){
        return this.reviewService.getLatestReviews();
    }

    @GetMapping("/{id}")
    public List<Review> getAllReviewsForPlace(@PathVariable Long id){
        return this.reviewService.listAllReviewsByPlaceId(id);
    }
}
