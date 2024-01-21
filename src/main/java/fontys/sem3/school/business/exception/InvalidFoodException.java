package fontys.sem3.school.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFoodException extends ResponseStatusException {
    public InvalidFoodException() {
        super(HttpStatus.BAD_REQUEST, "FOOD NOT FOUND");
    }

}
