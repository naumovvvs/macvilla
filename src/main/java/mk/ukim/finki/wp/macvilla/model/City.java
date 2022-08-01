package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import javax.persistence.*;

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

    public City(){
    }

    public City(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.thumbnail = image;
    }
}
