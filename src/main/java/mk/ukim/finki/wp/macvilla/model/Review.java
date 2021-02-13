package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

@Data
public class Review {
    private Long reviewId;
    private String content;
    private Float rating;
    // FK to the author
    private Long authorId;
    // FK to the place
    private Long placeId;

    public Review(){
        this.placeId = null;
        this.authorId = null;
    }

    public Review(String content, Float rating, Long authorId, Long placeId){
        this.content = content;
        this.rating = rating;
        this.authorId = authorId;
        this.placeId = placeId;
    }
}
