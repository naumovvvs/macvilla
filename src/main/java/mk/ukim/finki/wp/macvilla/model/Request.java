package mk.ukim.finki.wp.macvilla.model;

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
    @ManyToOne
    private Hotelier requestAuthor;

    // time when the request was created
    private Date requestTime;

    // FK to the place for which the request was made
    @ManyToOne
    private Place place;

    // Status of the request
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    public Request(){
        this.requestTime = null;
        this.place = null;
        this.requestStatus = RequestStatus.PENDING;
    }

    public Request(Place place) {
        //requestTime contains current date and time, when the request was made
        long millis = System.currentTimeMillis();
        this.requestTime = new Date(millis);
        this.place = place;
        this.requestStatus = RequestStatus.PENDING;
    }
}
