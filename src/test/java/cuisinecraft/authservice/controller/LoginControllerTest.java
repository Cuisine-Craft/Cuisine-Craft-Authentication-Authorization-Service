package cuisinecraft.authservice.controller;

import cuisinecraft.authservice.business.LoginUseCase;
import cuisinecraft.authservice.domain.LoginRequest;
import cuisinecraft.authservice.domain.LoginResponse;
import cuisinecraft.authservice.domain.RefreshTokenRequest;
import cuisinecraft.authservice.domain.RefreshTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUseCase loginUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequest loginRequest;
    private RefreshTokenRequest refreshTokenRequest;

    @BeforeEach
    public void setup() {
        loginRequest = new LoginRequest("user@example.com", "password123");
        refreshTokenRequest = new RefreshTokenRequest("sample_refresh_token");
    }

//    @Test
//    public void testLogin() throws Exception {
//        LoginResponse loginResponse = new LoginResponse("sample_access_token", "sample_refresh_token");
//
//        // Mock the response from LoginUseCase
//        Mockito.when(loginUseCase.login(any(LoginRequest.class))).thenReturn(loginResponse);
//
//        mockMvc.perform(post("/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.accessToken").value("sample_access_token"))
//                .andExpect(jsonPath("$.refreshToken").value("sample_refresh_token"));
//    }

    @Test
    public void testRefreshToken() throws Exception {
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse("new_access_token");

        // Mock the response from LoginUseCase
        Mockito.when(loginUseCase.refreshToken(any(RefreshTokenRequest.class))).thenReturn(refreshTokenResponse);

        mockMvc.perform(post("/user/login/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshTokenRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new_access_token"));
    }
}
