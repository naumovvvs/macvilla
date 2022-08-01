package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String content;
    private Float rating;
    // date when the user posted the review
    private Date reviewDate;

    // FK to the author
    @ManyToOne
    private Client author;
    // FK to the place
    @ManyToOne
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
        long millis = System.currentTimeMillis();
        this.reviewDate = new Date(millis);
    }
}
