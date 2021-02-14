package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import mk.ukim.finki.wp.macvilla.model.enums.RequestStatus;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
        // TODO: get current time
        this.requestTime = new Date();
        this.place = place;
        this.requestStatus = RequestStatus.PENDING;
    }
}
