package cuisinecraft.authservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse {
    private Long Id;
    private String accessToken;
}
