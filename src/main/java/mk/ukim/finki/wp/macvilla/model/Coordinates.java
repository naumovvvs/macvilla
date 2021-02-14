package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Coordinates {
    @Id
    private Float latitude;
    @Id
    private Float longitude;
    // FK to the place for which the coordinates are for
    @OneToOne
    private Place place;

    public Coordinates(Float latitude, Float longitude, Place place){
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
    }
}
