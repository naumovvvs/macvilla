package mk.ukim.finki.wp.macvilla.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import mk.ukim.finki.wp.macvilla.model.enums.Role;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Hotelier extends User{
    @JsonManagedReference
    @OneToMany(mappedBy = "requestAuthor", fetch = FetchType.EAGER)
    private List<Request> madeRequests;

    public Hotelier(){
        super();
        this.madeRequests = new ArrayList<>();
    }

    public Hotelier(String username, String password, String name, String surname, String email, String avatarURL){
        super(username, password, name, surname, email, avatarURL, Role.ROLE_HOTELIER);
        this.madeRequests = new ArrayList<>();
    }
}
