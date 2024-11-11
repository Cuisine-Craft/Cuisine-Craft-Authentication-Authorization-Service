package cuisinecraft.authservice.business;

import cuisinecraft.authservice.domain.LoginRequest;
import cuisinecraft.authservice.domain.LoginResponse;
import cuisinecraft.authservice.domain.RefreshTokenRequest;
import cuisinecraft.authservice.domain.RefreshTokenResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
