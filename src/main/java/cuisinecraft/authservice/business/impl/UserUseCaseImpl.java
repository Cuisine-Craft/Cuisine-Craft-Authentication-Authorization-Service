package cuisinecraft.authservice.business.impl;

import cuisinecraft.authservice.business.UserValidator;
import cuisinecraft.authservice.business.UserUseCase;
import cuisinecraft.authservice.business.exception.InvalidUserException;
import cuisinecraft.authservice.domain.*;
import cuisinecraft.authservice.persistence.UserRepository;
import cuisinecraft.authservice.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserUseCaseImpl implements UserUseCase {
    private final UserRepository userRepository;
    private final UserValidator userIdValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void deleteUser(long UserId) {

        this.userRepository.deleteById(UserId);
    }
    @Override
    public GetAllUsersResponse getUsers() {
        List<User> Users = userRepository.findAll()
                .stream()
                .map(UserConverter::convert)
                .toList();

        return GetAllUsersResponse.builder()
                .Users(Users)
                .build();
    }
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {


        UserEntity savedUser = saveNewUser(request);

        return CreateUserResponse.builder()
                .Id(savedUser.getId())
                .build();
    }
    @Override
    public Optional<User> getUser(long UserId) {
        return userRepository.findById(UserId).map(UserConverter::convert);

    }
    private UserEntity saveNewUser(CreateUserRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPasswordhash());
        UserEntity newUser = UserEntity.builder()
                .name(request.getName())
                .username(request.getUsername())
                .passwordhash(encodedPassword)
                .phonenumber(request.getPhonenumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .gender(request.getGender())
                .profilePictureUrl(request.getProfilePictureUrl())
                .birthdate(request.getBirthdate())
                .role(request.getRole())
                .balance((double) 0)
                .build();
        return userRepository.save(newUser);
    }
    @Override
    public void updateUser(UpdateUserRequest request) {
        Optional<UserEntity> UserOptional = userRepository.findById(request.getId());


        userIdValidator.validateId(request.getId());

        if (UserOptional.isPresent()) {
            UserEntity user = UserOptional.get();
            updateFieldsProfile(request, user);
        } else {
            throw new InvalidUserException("USER_ID_INVALID");

        }
    }


    private void updateFieldsProfile(UpdateUserRequest request, UserEntity User) {
        User.setName(request.getName());
        User.setId(request.getId());


        User.setPhonenumber(request.getPhonenumber());

        userRepository.save(User);
    }
    @Override
    public void updateUserBalance(UpdateUserBalanceRequest request) {
        Optional<UserEntity> UserOptional = userRepository.findById(request.getId());


        userIdValidator.validateId(request.getId());
        if (UserOptional.isPresent()) {
            UserEntity User = UserOptional.get();
            updatebalance(request, User);
        } else {
            throw new InvalidUserException("USER_ID_INVALID");

        }

    }
    private void updatebalance(UpdateUserBalanceRequest request, UserEntity User) {
        if (request.isUpdate()){
            User.setBalance(User.getBalance()+request.getAmount());
        } else{
            User.setBalance(User.getBalance()-request.getAmount());
        }
        userRepository.save(User);

    }

}
