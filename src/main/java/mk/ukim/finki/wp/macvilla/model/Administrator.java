package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import mk.ukim.finki.wp.macvilla.model.enums.Role;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Administrator extends User {
    // list of pending requests that the admin needs to review
    @Transient
    private List<Request> pendingRequests;

    // list of requests that the admin approved for publishing
    @Transient
    private List<Request> approvedRequests;

    // list of requests that the admin denied from publishing due to some reasons
    @Transient
    private List<Request> deniedRequests;

    // list of users that the admin blocked for certain reasons
    @Transient
    private List<User> blockedUsers;

    public Administrator(){
        super();
        this.pendingRequests = new ArrayList<>();
        this.approvedRequests = new ArrayList<>();
        this.deniedRequests = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }

    public Administrator(String username, String password, String name, String surname, String email, String avatarURL){
        super(username, password, name, surname, email, avatarURL, Role.ROLE_ADMIN);
        this.pendingRequests = new ArrayList<>();
        this.approvedRequests = new ArrayList<>();
        this.deniedRequests = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }
}
