package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

@Data
public class Review {
    private Long reviewId;
    private String content;
    private Float rating;
    // FK to the author
    private Client author;
    // FK to the place
    private Place place;

    public Review(){
        this.place = null;
        this.author = null;
    }

    public Review(String content, Float rating, Client author, Place place){
        this.content = content;
        this.rating = rating;
        this.author = author;
        this.place = place;
    }
}
