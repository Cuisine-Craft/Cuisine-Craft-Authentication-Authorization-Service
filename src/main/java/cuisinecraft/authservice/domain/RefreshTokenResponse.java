package cuisinecraft.authservice.domain;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
    String token;
}
