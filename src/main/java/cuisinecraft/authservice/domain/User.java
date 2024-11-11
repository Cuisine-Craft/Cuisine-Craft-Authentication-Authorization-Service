package cuisinecraft.authservice.domain;


import cuisinecraft.authservice.domain.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//asd
public class User {
    private Long id;


    private String name;


    private String username;


    private String passwordhash;


    private String phonenumber;
    private String email;

    private String address;


    private String gender;


    private String profilePictureUrl;
    private Role role;

    private LocalDate birthdate;


    private Double balance;
}
