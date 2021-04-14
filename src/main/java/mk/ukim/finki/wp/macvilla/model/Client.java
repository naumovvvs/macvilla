package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import mk.ukim.finki.wp.macvilla.model.enums.Role;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Client extends User{
    private LocalDate birthDate;
    // address of the client (city is important for filtering reasons)
    private String address;
    // list of favorite places
    @ManyToMany
    private List<Place> favoritePlaces;

    public Client(){
        super();
        this.favoritePlaces = new ArrayList<>();
    }

    public Client(String username, String password, String name, String surname, String email, String avatarURL,
                  LocalDate birthDate, String address){
        super(username, password, name, surname, email, avatarURL, Role.ROLE_CLIENT);
        this.birthDate = birthDate;
        this.address = address;
        this.favoritePlaces = new ArrayList<>();
    }
}
