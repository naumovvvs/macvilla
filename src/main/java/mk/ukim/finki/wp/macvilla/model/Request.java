package mk.ukim.finki.wp.macvilla.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    // author of the request (manager)
    @JsonBackReference
    @ManyToOne
    private Hotelier requestAuthor;

    // time when the request was created
    private Date requestTime;

    // Status of the request
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    public Request(){
        long millis = System.currentTimeMillis();
        this.requestTime = new Date(millis);
        this.requestStatus = RequestStatus.PENDING;
    }

    public Request(Hotelier author){
        //requestTime contains current date and time, when the request was made
        long millis = System.currentTimeMillis();
        this.requestTime = new Date(millis);
        this.requestStatus = RequestStatus.PENDING;
        this.requestAuthor = author;
    }
}
