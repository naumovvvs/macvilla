package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

@Data
public class Coordinates {
    private Float latitude;
    private Float longitude;
    // FK to the place for which the coordinates are for
    private Long placeId;

    public Coordinates(Float latitude, Float longitude, Long placeId){
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
    }
}
