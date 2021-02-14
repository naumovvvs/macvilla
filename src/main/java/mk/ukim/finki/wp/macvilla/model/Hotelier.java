package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Hotelier extends User{
    // list of places that the manager manages
    @OneToMany
    private List<Place> managedPlaces;

    // list of requests made by the manager
    @OneToMany
    private List<Hotelier> madeRequests;

    public Hotelier(){
        super();
        this.managedPlaces = new ArrayList<>();
        this.madeRequests = new ArrayList<>();
    }

    public Hotelier(String username, String password, String name, String surname, String email, String avatarURL){
        super(username, password, name, surname, email, avatarURL);
        this.managedPlaces = new ArrayList<>();
        this.madeRequests = new ArrayList<>();
    }
}
