package mk.ukim.finki.wp.macvilla.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String category){
        super(String.format("Category %s is invalid", category));
    }
}
