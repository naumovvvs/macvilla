package mk.ukim.finki.wp.macvilla.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidUserIdException extends RuntimeException{
    public InvalidUserIdException(Long userId){
        super(String.format("Invalid User ID: %d", userId));
    }
}
