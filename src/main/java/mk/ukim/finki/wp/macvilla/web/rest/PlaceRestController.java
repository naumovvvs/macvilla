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
import java.awt.font.OpenType;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/place")
public class PlaceRestController {

    private final PlaceService placeService;
    private final ReviewService reviewService;
    private final UserService userService;

    public PlaceRestController(PlaceService placeService, ReviewService reviewService, UserService userService) {
        this.placeService = placeService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Place getPlace(@PathVariable Long id){
        return this.placeService.findById(id).orElseThrow(() -> new PlaceNotFoundException(id));
    }

    @GetMapping("/{id}/reviews")
    public List<Review> getAllReviews(@PathVariable Long id){
        return this.reviewService.listAllReviewsByPlaceId(id);
    }

    @PostMapping("/{id}/reviews/new")
    public void postReview(@PathVariable Long id, @RequestParam String ratingStars, @RequestParam String reviewContent,
                           HttpServletRequest request){
        Place place = this.placeService.findById(id).orElseThrow(() -> new PlaceNotFoundException(id));

        float rating = 1.0f;

        switch (ratingStars) {
            case "two_stars":
                rating = 2.0f;
                break;
            case "three_stars":
                rating = 3.0f;
                break;
            case "four_stars":
                rating = 4.0f;
                break;
            case "five_stars":
                rating = 5.0f;
                break;
        }

        Optional<User> client = this.userService.findByUsername(request.getRemoteUser());

        // if the user doesn't exist don't post the review
        if(client.isPresent()) {
            this.reviewService.create(reviewContent, rating, (Client)client.get(), place);
        }

        //TODO redirect to placePage

    }
}
