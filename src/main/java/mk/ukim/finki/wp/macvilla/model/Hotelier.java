package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Hotelier extends User{
    // list of places that the manager manages
    private List<Long> managedPlaces;

    public Hotelier(){
        super();
        this.managedPlaces = new ArrayList<>();
    }

    public Hotelier(String username, String password, String name, String surname, String email, String avatarURL){
        super(username, password, name, surname, email, avatarURL);
        this.managedPlaces = new ArrayList<>();
    }
}
