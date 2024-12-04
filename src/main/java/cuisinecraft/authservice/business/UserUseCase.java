package cuisinecraft.authservice.business;

import cuisinecraft.authservice.domain.*;

import java.util.Optional;

public interface UserUseCase {
    void deleteUser(long UserId);

    GetAllUsersResponse getUsers();

    CreateUserResponse createUser(CreateUserRequest request);

    Optional<User> getUser(long UserId);

    void updateUser(UpdateUserRequest request);

}
