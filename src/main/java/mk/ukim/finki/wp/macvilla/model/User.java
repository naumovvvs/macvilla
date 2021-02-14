package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    // URL for avatar profile picture
    private String avatarURL;
    // Status of the user
    private boolean isBlocked;

    public User(){
        this.isBlocked = false;
    }

    public User(String username, String password, String name, String surname, String email, String avatarURL){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isBlocked = false;

        if(avatarURL!=null && !avatarURL.isEmpty()) {
            this.avatarURL = avatarURL;
        }
    }
}
