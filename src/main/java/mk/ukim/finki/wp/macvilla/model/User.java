package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    // URL for avatar profile picture
    private String avatarURL;
    // Status of the user
    private boolean blocked;

    public User(){
        this.blocked = false;
    }

    public User(String username, String password, String name, String surname, String email, String avatarURL){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.blocked = false;

        if(avatarURL!=null && !avatarURL.isEmpty()) {
            this.avatarURL = avatarURL;
        }
    }
}
