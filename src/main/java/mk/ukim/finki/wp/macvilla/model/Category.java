package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;

    public Category(){
    }

    public Category(String name) {
        this.name = name;
        this.description = "";
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
