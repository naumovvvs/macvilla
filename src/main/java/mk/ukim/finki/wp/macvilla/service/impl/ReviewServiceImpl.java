package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Place;
import mk.ukim.finki.wp.macvilla.model.Review;
import mk.ukim.finki.wp.macvilla.model.exceptions.PlaceNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.ReviewRepository;
import mk.ukim.finki.wp.macvilla.service.PlaceService;
import mk.ukim.finki.wp.macvilla.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PlaceService placeService) {
        this.reviewRepository = reviewRepository;
        this.placeService = placeService;
    }

    @Override
    public Review create(String content, Float rating, Long authorId, Long placeId) {
        //TODO: After user service is created
        return null;
    }

    @Override
    public List<Review> listAllReviewsByPlaceId(Long placeId) {
        this.placeService.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
        return this.reviewRepository.findAll()
                .stream().filter(r -> r.getPlaceId().equals(placeId)).collect(Collectors.toList());
    }

    @Override
    public List<Review> listAllReviewsByAuthorId(Long authorId) {
        //TODO: When user service is created
        return null;
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
}
