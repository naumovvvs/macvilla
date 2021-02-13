package mk.ukim.finki.wp.macvilla.model;

import lombok.Data;
import java.util.Date;

@Data
public class Request {
    private Long requestId;
    // time when the request was created
    private Date requestTime;
    // FK to the place for which the request was made
    private Long placeId;

    public Request(){
        this.requestTime = null;
        this.placeId = null;
    }

    public Request(Long placeId) {
        // TODO: get current time
        this.requestTime = new Date();
        this.placeId = placeId;
    }
}
