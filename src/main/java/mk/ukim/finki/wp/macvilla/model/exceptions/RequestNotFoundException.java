package mk.ukim.finki.wp.macvilla.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException(Long id) {
        super(String.format("Request with id %d was not found!", id));
    }
}
