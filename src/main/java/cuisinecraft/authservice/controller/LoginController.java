package cuisinecraft.authservice.controller;

import cuisinecraft.authservice.business.LoginUseCase;
import cuisinecraft.authservice.domain.LoginRequest;
import cuisinecraft.authservice.domain.LoginResponse;
import cuisinecraft.authservice.domain.RefreshTokenRequest;
import cuisinecraft.authservice.domain.RefreshTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
    @PostMapping("/token")
    public ResponseEntity<RefreshTokenResponse>refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok().body(loginUseCase.refreshToken(request));
    }
}
