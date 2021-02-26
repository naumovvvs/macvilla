package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    private String name;
    private String description;

    @OneToOne
    private Image thumbnail;
//    // list of places in a specific city
//    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
//    private List<Place> listPlaces;

    public City(){
        //this.listPlaces = new ArrayList<>();
    }

    public City(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.thumbnail = image;
        //this.listPlaces = new ArrayList<>();
    }
}
