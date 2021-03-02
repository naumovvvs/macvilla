package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;
    // FK to the manager that registered the place
    @ManyToOne
    private Hotelier manager;

    // FK to the made request
    @OneToOne
    private Request request;

    // FK to the City in which the place is situated
    @ManyToOne
    private City city;
    private String name;
    private String description;
    private String address;
    private String telephoneNumber;

    @ManyToOne
    private Category category;
    private Float rating;

    private float ratingSum;
    private int ratingCount;

    @Transient
    private boolean show = true; // used in frontend for showing based on rating

    // list of pictures
    @OneToMany
    private List<Image> gallery;
    // URL of thumbnail
    @OneToOne
    private Image thumbnail;
    // number of clicks
    private Integer visits;
//    // coordinates of place
    @OneToOne
    private Coordinates location;

    public Place(){
        this.gallery = new ArrayList<>();
    }

    public Place(Hotelier manager, City city, String name, String description, String address,
                 String telephoneNumber, Category category,
                 List<Image> gallery, Image thumbnail, Request request) {
        this.manager = manager;
        this.city = city;
        this.name = name;
        this.description = description;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.category = category;
        this.gallery = gallery;
        this.thumbnail = thumbnail;
//      this.map = map;
        this.visits = 0;

        this.ratingSum = 0.0f;
        this.ratingCount = 0;
        this.rating = 5.0F;

        this.request = request;
    }
}
