package cuisinecraft.authservice.business.impl;

import cuisinecraft.authservice.business.UserValidator;
import cuisinecraft.authservice.business.exception.InvalidUserException;
import cuisinecraft.authservice.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidatorImpl implements UserValidator {
    private final UserRepository userRepository;


    @Override
    public void validateId(Long UserId) {
        if (UserId == null || !userRepository.existsById(UserId)) {
            throw new InvalidUserException("USER_ID_INVALID");
        }
    }
    @Override
    public void validateBalance(Double UserBalance,Double Update) {
        if (UserBalance-Update<0) {
            throw new InvalidUserException("USER_BALANCE_INSUFFICIENT");
        }
    }

}
