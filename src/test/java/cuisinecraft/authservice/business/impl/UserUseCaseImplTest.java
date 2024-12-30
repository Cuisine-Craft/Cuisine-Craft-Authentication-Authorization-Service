package cuisinecraft.authservice.business.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cuisinecraft.authservice.business.UserValidator;
import cuisinecraft.authservice.business.exception.InvalidUserException;
import cuisinecraft.authservice.domain.*;
import cuisinecraft.authservice.domain.Enum.Role;
import cuisinecraft.authservice.persistence.UserRepository;
import cuisinecraft.authservice.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class UserUseCaseImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserValidator mockUserIdValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserUseCaseImpl userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCaseImpl(mockUserRepository, mockUserIdValidator,passwordEncoder);
    }

    @Test
    void testCreateUserSuccess() {
        // Arrange
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .name("John Doe")
                .username("johndoe")
                .passwordhash("password123")
                .phonenumber("1234567890")
                .address("123 Main St")
                .role(Role.User)
                .build();

        UserEntity savedUserEntity = UserEntity.builder()
                .id(1L) // Mocked or actual ID returned by the database
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .passwordhash(userRequest.getPasswordhash())
                .phonenumber(userRequest.getPhonenumber())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .build();

        when(mockUserRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);

        // Act
        CreateUserResponse response = userUseCase.createUser(userRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void deleteUser() {
        // Arrange
        long userId = 1L;

        // Act
        userUseCase.deleteUser(userId);

        // Assert
        verify(mockUserRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUsers() {
        // Arrange
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .name("John Doe")
                .username("johndoe")
                .passwordhash("password123")
                .phonenumber("1234567890")
                .email("act@gmail.com")
                .address("123 Main St")
                .role(Role.User)  // Assuming you have an Enum called Role with USER as one of its values
                // Replace this with the actual birthdate
                .build();
        UserEntity savedUserEntity =   UserEntity.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .passwordhash(userRequest.getPasswordhash())
                .phonenumber(userRequest.getPhonenumber())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .build();

        when(mockUserRepository.findAll()).thenReturn(List.of(savedUserEntity));

        // Act
        GetAllUsersResponse response = userUseCase.getUsers();

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getUsers().size());
    }

    @Test
    void getUser() {
        // Arrange
        long userId = 1L;
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .name("John Doe")
                .username("johndoe")
                .passwordhash("password123")
                .phonenumber("1234567890")
                .email("act@gmail.com")
                .address("123 Main St")
                .role(Role.User)  // Assuming you have an Enum called Role with USER as one of its values// Replace this with the actual birthdate
                .build();
        UserEntity savedUserEntity =   UserEntity.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .passwordhash(userRequest.getPasswordhash())
                .phonenumber(userRequest.getPhonenumber())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .build();

        when(mockUserRepository.findById(userId)).thenReturn(Optional.ofNullable(savedUserEntity));

        // Act
        Optional<User> response = userUseCase.getUser(userId);

        // Assert
        assertTrue(response.isPresent());
        assertEquals("John Doe", response.get().getName());
    }

    @Test
    void testUpdateUserSuccess() {
        // Arrange
        long existingUserId = 1L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(
                existingUserId, "Updated John Doe", "1234567890");

        UserEntity existingUserEntity = UserEntity.builder()
                .id(existingUserId)
                .name("John Doe")
                .username("johndoe")
                .passwordhash("password123")
                .phonenumber("1234567890")
                .email("act@gmail.com")
                .address("123 Main St")
                .role(Role.User)
                .build();

        when(mockUserRepository.findById(existingUserId)).thenReturn(Optional.ofNullable(existingUserEntity));

        // Act
        userUseCase.updateUser(updateUserRequest);

        // Assert
        verify(mockUserIdValidator, times(1)).validateId(existingUserId);
        assertEquals("Updated John Doe", existingUserEntity.getName());
    }


    @Test
    void testUpdateUserNonExistentUser() {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest(/* provide necessary parameters */);

        when(mockUserRepository.findById(request.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(InvalidUserException.class, () -> userUseCase.updateUser(request));
    }





    // Add more tests as needed for other methods and edge cases
}
