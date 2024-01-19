package fontys.sem3.school.domain;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//asd
public class OrderDetailRequest {

    private Long id;

    private Long orderheaderid;

    @NotNull
    private long foodid;

    @NotNull
    private int amount;

    @NotNull
    private double subtotal;

    private String specialRequest;
}
