package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cityId;
    private String name;
    private String description;
    // list of places in a specific city
    @OneToMany
    private List<Place> listPlaces;

    public City(){
        this.listPlaces = new ArrayList<>();
    }

    public City(String name, String description) {
        this.name = name;
        this.description = description;
        this.listPlaces = new ArrayList<>();
    }
}
