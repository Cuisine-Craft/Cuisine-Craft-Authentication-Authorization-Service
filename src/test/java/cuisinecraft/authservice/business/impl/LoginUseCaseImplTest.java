package cuisinecraft.authservice.business.impl;

import cuisinecraft.authservice.business.exception.InvalidCredentialsException;
import cuisinecraft.authservice.business.exception.InvalidTokenException;
import cuisinecraft.authservice.configuration.security.token.AccessTokenEncoder;
import cuisinecraft.authservice.configuration.security.token.impl.AccessTokenImpl;
import cuisinecraft.authservice.domain.*;
import cuisinecraft.authservice.domain.Enum.Role;
import cuisinecraft.authservice.persistence.UserRepository;
import cuisinecraft.authservice.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginUseCase = new LoginUseCaseImpl(userRepository, passwordEncoder,accessTokenEncoder);
    }
    @Test
    public void testLogin_Successful() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("johndoe", "johndoe");
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .name("John Doe")
                .username("johndoe")
                .passwordhash("$2a$10$rd3wel7MDAix.dcb3KvXFu3LAF6WSxits6m7PmRUizlbQC63vqgsO")
                .phonenumber("1234567890")
                .address("123 Main St")
                .role(Role.User)
                .build();
        UserEntity user = UserEntity.builder()
                .id(1L) // Mocked or actual ID returned by the database
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .passwordhash(userRequest.getPasswordhash())
                .phonenumber(userRequest.getPhonenumber())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .build();
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordhash())).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn("mockedAccessToken");

        // Act
        LoginResponse response = loginUseCase.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertEquals("mockedAccessToken", response.getAccessToken());

        // Verify interactions
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPasswordhash());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }

    @Test
    public void testLogin_InvalidUsername() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("nonexistentUser", "password");
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(null);

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));

        // Verify interactions
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
        verifyNoInteractions(passwordEncoder, accessTokenEncoder);
    }

    @Test
    public void testLogin_InvalidPassword() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("johndoe", "invalid");
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .name("John Doe")
                .username("johndoe")
                .passwordhash("$2a$10$rd3wel7MDAix.dcb3KvXFu3LAF6WSxits6m7PmRUizlbQC63vqgsO")
                .phonenumber("1234567890")
                .address("123 Main St")
                .role(Role.User)
                .build();
        UserEntity user = UserEntity.builder()
                .id(1L) // Mocked or actual ID returned by the database
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .passwordhash(userRequest.getPasswordhash())
                .phonenumber(userRequest.getPhonenumber())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .build();
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordhash())).thenReturn(false);

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));

        // Verify interactions
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPasswordhash());
        verifyNoInteractions(accessTokenEncoder);
    }

    @Test
    void refreshTokenShouldThrowInvalidTokenExceptionWhenTokenNotExists() {
        // Arrange
        RefreshTokenRequest request = new RefreshTokenRequest("nonexistent-token");
        when(userRepository.findByToken(request.getToken())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidTokenException.class, () -> loginUseCase.refreshToken(request));

        // Verify
        verify(userRepository, times(1)).findByToken(request.getToken());
        verifyNoMoreInteractions(userRepository, accessTokenEncoder);
    }

    @Test
    void refreshTokenShouldReturnNewAccessTokenWhenOldTokenExists() {
        // Arrange
        RefreshTokenRequest request = new RefreshTokenRequest("existing-token");
        UserEntity userEntity = new UserEntity(); // create a user entity as needed
        when(userRepository.findByToken(request.getToken())).thenReturn(Optional.of(userEntity));

        String newAccessToken = "new-access-token";
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn(newAccessToken);

        // Act
        RefreshTokenResponse response = loginUseCase.refreshToken(request);

        // Assert
        assertNotNull(response);
        assertEquals(newAccessToken, response.getToken());

        // Verify
        verify(userRepository, times(1)).findByToken(request.getToken());
        verify(userRepository, times(1)).saveToken(newAccessToken, userEntity.getUsername());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
        verifyNoMoreInteractions(userRepository, accessTokenEncoder);
    }
}