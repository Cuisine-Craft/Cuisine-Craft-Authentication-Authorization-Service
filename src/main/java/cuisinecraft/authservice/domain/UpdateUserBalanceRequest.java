package cuisinecraft.authservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserBalanceRequest {
    @NotNull
    private Long id;
    @NotNull
    private Double amount;
    @NotNull
    private boolean update;



}
