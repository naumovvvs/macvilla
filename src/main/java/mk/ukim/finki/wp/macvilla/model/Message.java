package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String userSurname;

    private String userEmail;

    private String content;

    public Message() {
    }

    public Message(String userName, String userSurname, String userEmail, String content) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.content = content;
    }
}
