package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.Review;
import mk.ukim.finki.wp.macvilla.model.exceptions.ClientNotFoundException;
import mk.ukim.finki.wp.macvilla.model.exceptions.PlaceNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.PlaceRepository;
import mk.ukim.finki.wp.macvilla.repository.ReviewRepository;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import mk.ukim.finki.wp.macvilla.service.ReviewService;
import mk.ukim.finki.wp.macvilla.service.UserService;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;
    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PlaceService placeService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.placeService = placeService;
        this.userService = userService;
    }

    @Override
    public Review create(String content, Float rating, Client author, Place place) {
        Place tempPlace = this.placeService.findById(place.getPlaceId())
                .orElseThrow(() -> new PlaceNotFoundException(place.getPlaceId()));

        this.userService.findById(author.getUserId())
                .orElseThrow(() -> new ClientNotFoundException(author.getUserId()));

        tempPlace.setRatingSum(tempPlace.getRatingSum()+rating);
        tempPlace.setRatingCount(tempPlace.getRatingCount()+1);

        this.placeService.update(tempPlace.getPlaceId(), tempPlace.getCity().getCityId(), tempPlace.getName(),
                tempPlace.getDescription(), tempPlace.getAddress(), tempPlace.getTelephoneNumber(),
                tempPlace.getCategory().getCategoryId(), tempPlace.getGallery(), tempPlace.getThumbnail(),
                tempPlace.getRatingSum(), tempPlace.getRatingCount());

        return this.reviewRepository.save(new Review(content, rating, author, tempPlace));
    }

    @Override
    public List<Review> listAllReviewsByPlaceId(Long placeId) {
        Place place = this.placeService.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
        return this.reviewRepository.findAll()
                .stream().filter(r -> r.getPlace().getPlaceId().equals(place.getPlaceId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> listAllReviewsByAuthorId(Long authorId) {
        Client client = (Client) this.userService.findById(authorId)
                .orElseThrow(() -> new ClientNotFoundException(authorId));
        return this.reviewRepository.findAll()
                .stream().filter(r -> r.getAuthor().getUserId().equals(client.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> listAllReviewsByRating(Float rating, boolean ascending) {
        if(ascending){
            // ascending order
            return this.reviewRepository.findAllByRatingGreaterThanEqual(rating).stream()
                    .sorted(Comparator.comparing(Review::getRating)).collect(Collectors.toList());
        }else{
            // descending order
            return this.reviewRepository.findAllByRatingGreaterThanEqual(rating).stream()
                    .sorted(Comparator.comparing(Review::getRating).reversed()).collect(Collectors.toList());
        }
    }

    @Override
    public void removeAllWithPlace(Place place) {
        this.reviewRepository.deleteReviewsByPlace(place);
    }

    @Override
    public List<Review> getLatestReviews() {
        // returns latest six reviews
        return this.reviewRepository.findAll().stream()
                .sorted(Comparator.comparing(Review::getReviewDate)).limit(6).collect(Collectors.toList());
    }
}
