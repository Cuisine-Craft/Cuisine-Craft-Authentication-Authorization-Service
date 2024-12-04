package cuisinecraft.authservice.persistence.entity;

import cuisinecraft.authservice.domain.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Length(max = 60)
    @Column(name = "password_hash")
    private String passwordhash;

    @NotBlank
    @Length(max = 15)
    @Column(name = "phonenumber") // Specify the column type and length
    private String phonenumber;

    @NotBlank
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Length(max = 50)
    @Column(name = "address") // Specify the column type and length
    private String address;


    @NotNull
    @Column(name = "role")
    private Role role;
       

    @Column(name = "token",columnDefinition = "TEXT")
    private String token;


    
}
