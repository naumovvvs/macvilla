package mk.ukim.finki.wp.macvilla.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException(){
        super("The username is already taken");
    }
}
