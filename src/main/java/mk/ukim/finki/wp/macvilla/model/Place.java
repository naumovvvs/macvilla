package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Place {
    private Long placeId;
    // FK to the manager that registered the place
    private Long managerId;
    private String name;
    private String description;
    private String address;
    private String telephoneNumber;
    private Integer price;
    private String category;
    private Float rating;
    // list of pictures
    private List<String> gallery;
    // URL of thumbnail
    private String thumbnail;
    // number of clicks
    private Integer visits;
    // coordinates of place
    private Coordinates map;

    public Place(){
        this.gallery = new ArrayList<>();
    }

    public Place(Long managerId, String name, String description, String address,
                 String telephoneNumber, Integer price, String category,
                 List<String> gallery, String thumbnail, Coordinates map) {
        this.managerId = managerId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.price = price;
        this.category = category;
        this.gallery = gallery;
        this.thumbnail = thumbnail;
        this.map = map;
        this.visits = 0;
        this.rating = 0.0F;
        this.gallery = new ArrayList<>();
    }
}
