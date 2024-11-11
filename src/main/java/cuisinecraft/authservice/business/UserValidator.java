package cuisinecraft.authservice.business;

import cuisinecraft.authservice.business.exception.InvalidUserException;
import cuisinecraft.authservice.business.exception.UserInsufficientException;

public interface UserValidator {
    void validateId(Long userID) throws InvalidUserException;
    void validateBalance(Double UserBalance,Double Update) throws UserInsufficientException;


}
