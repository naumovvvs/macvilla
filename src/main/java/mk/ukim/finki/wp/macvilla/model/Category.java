package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

@Data
public class Category {
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
