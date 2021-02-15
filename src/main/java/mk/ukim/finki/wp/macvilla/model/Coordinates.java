package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long coordinatesId;
    private Float latitude;
    private Float longitude;
    // FK to the place for which the coordinates are for
    @OneToOne
    private Place place;

    public Coordinates() {
    }

    public Coordinates(Float latitude, Float longitude, Place place){
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
    }
}
