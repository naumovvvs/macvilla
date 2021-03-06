package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Client extends User{
    private Date birthDate;
    // address of the client (city is important for filtering reasons)
    private String address;
    // list of favorite places
    private List<Long> favoritePlaces;


    public Client(){
        super();
        this.favoritePlaces = new ArrayList<>();
    }

    public Client(String username, String password, String name, String surname, String email, String avatarURL,
                  Date birthDate, String address){
        super(username, password, name, surname, email, avatarURL);
        this.birthDate = birthDate;
        this.address = address;
        this.favoritePlaces = new ArrayList<>();
    }
}
