package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;
    private String content;
    private Float rating;
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
    }
}
