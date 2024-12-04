package cuisinecraft.authservice.business.impl;

import cuisinecraft.authservice.business.LoginUseCase;
import cuisinecraft.authservice.business.exception.InvalidCredentialsException;
import cuisinecraft.authservice.business.exception.InvalidTokenException;
import cuisinecraft.authservice.configuration.security.token.AccessTokenEncoder;
import cuisinecraft.authservice.configuration.security.token.impl.AccessTokenImpl;
import cuisinecraft.authservice.domain.LoginRequest;
import cuisinecraft.authservice.domain.LoginResponse;
import cuisinecraft.authservice.domain.RefreshTokenRequest;
import cuisinecraft.authservice.domain.RefreshTokenResponse;
import cuisinecraft.authservice.persistence.UserRepository;
import cuisinecraft.authservice.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPasswordhash())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        userRepository.saveToken(accessToken,user.getUsername());
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {

        String role = String.valueOf(user.getRole());


        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getId(), role));
    }



    /**
     *
     * @param request Refresh Token request
     * @return Refresh Token Response
     *
     * @should throw InvalidUserException when token is not exists
     * @should return new AccessToken when old token exists
     */
    @Override
    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        Optional<UserEntity> optionalUser=userRepository.findByToken(request.getToken());
        if (optionalUser.isEmpty()){
            throw new InvalidTokenException();
        }

        UserEntity user=optionalUser.get();

        String accessToken = generateAccessToken(user);

        userRepository.saveToken(accessToken,user.getUsername());

        return RefreshTokenResponse.builder()
                .token(accessToken)
                .build();
    }
}
