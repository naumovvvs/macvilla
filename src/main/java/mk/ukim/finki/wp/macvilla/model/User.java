package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    // URL for avatar profile picture
    private String avatarURL;

    public User(){

    }

    public User(String username, String password, String name, String surname, String email, String avatarURL){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatarURL = avatarURL;
    }
}
