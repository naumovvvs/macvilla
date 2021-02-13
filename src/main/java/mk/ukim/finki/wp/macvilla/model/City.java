package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class City {
    private Long cityId;
    private String name;
    private String description;
    // list of places IDs in a specific city
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
