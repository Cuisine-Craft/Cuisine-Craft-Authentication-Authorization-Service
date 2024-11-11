package cuisinecraft.authservice.configuration.db;

import cuisinecraft.authservice.domain.Enum.Role;
import cuisinecraft.authservice.persistence.UserRepository;
import cuisinecraft.authservice.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {



    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        
        List<UserEntity> users = Arrays.asList(
                UserEntity.builder()
                        .name("johndoe")
                        .username("johndoe")
                        .passwordhash(passwordEncoder.encode("johndoe"))
                        .phonenumber("+312345678")
                        .email("john.doe@example.com")
                        .address("123 Main St")
                        .gender("Male")
                        .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/gourmet-eats.appspot.com/o/images%2Fuser%2Fjohndoe?alt=media&token=8615b91d-e8b4-49cf-92aa-a8b3a49cd798")
                        .role(Role.User)
                        .birthdate(LocalDate.of(1990, 1, 1))
                        .balance((double) 0)
                        .build(),
                UserEntity.builder()
                        .name("johndoe1")
                        .username("johndoe1")
                        .passwordhash(passwordEncoder.encode("johndoe1"))
                        .phonenumber("+3123456781")
                        .email("john.doe1@example.com")
                        .address("1231 Main St")
                        .gender("Male")
                        .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/gourmet-eats.appspot.com/o/images%2Fuser%2Fjohndoe1.jpg?alt=media&token=75c89139-3982-4781-a557-0ff42452fa28")
                        .role(Role.User)
                        .birthdate(LocalDate.of(1995, 1, 1))
                        .balance((double) 0)
                        .build(),
                UserEntity.builder()
                        .name("arielclarence")
                        .username("arielclarence")
                        .passwordhash(passwordEncoder.encode("@aCT190902"))
                        .phonenumber("+310684318401")
                        .email("ariel.clarence@example.com")
                        .address("grave")
                        .gender("Male")
                        .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/gourmet-eats.appspot.com/o/images%2Fuser%2Fjohndoe?alt=media&token=8615b91d-e8b4-49cf-92aa-a8b3a49cd798")
                        .role(Role.User)
                        .birthdate(LocalDate.of(2002, 9, 19))
                        .balance((double) 0)
                        .build(),
                UserEntity.builder()
                        .name("admin")
                        .username("admin")
                        .passwordhash(passwordEncoder.encode("admin"))
                        .phonenumber("+310684318401")
                        .email("admin@example.com")
                        .address("grave")
                        .gender("Female")
                        .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/gourmet-eats.appspot.com/o/images%2Fuser%2Fadmin.png?alt=media&token=dba9b8e9-6ce0-4289-a922-f455583bfc90")
                        .role(Role.Admin)
                        .birthdate(LocalDate.of(2002, 9, 19))
                        .balance((double) 0)
                        .build()
        );


        if (userRepository.count() == 0) {
            userRepository.saveAll(users);
        }



    }
}
