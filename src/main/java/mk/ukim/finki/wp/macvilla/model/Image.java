package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;
    private String imageURL;
    private String description;

    public Image(){
        this.imageURL = "";
        this.description = "";
    }

    public Image(String imageURL, String description){
        this.imageURL = imageURL;
        if(description==null){
            description = "";
        }
        this.description = description;
    }
}
