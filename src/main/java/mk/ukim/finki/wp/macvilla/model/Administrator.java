package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Administrator extends User{
    // list of pending requests that the admin needs to review
    private List<Long> pendingRequests;
    // list of requests that the admin approved for publishing
    private List<Long> approvedRequests;
    // list of requests that the admin denied from publishing due to some reasons
    private List<Long> deniedRequests;
    // list of users that the admin blocked for certain reasons
    private List<Long> blockedUsers;

    public Administrator(){
        super();
        this.pendingRequests = new ArrayList<>();
        this.approvedRequests = new ArrayList<>();
        this.deniedRequests = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }

    public Administrator(String username, String password, String name, String surname, String email, String avatarURL){
        super(username, password, name, surname, email, avatarURL);
        this.pendingRequests = new ArrayList<>();
        this.approvedRequests = new ArrayList<>();
        this.deniedRequests = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }
}
