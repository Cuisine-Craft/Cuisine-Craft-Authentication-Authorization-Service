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
                        .role(Role.User)
                        .build(),
                UserEntity.builder()
                        .name("johndoe1")
                        .username("johndoe1")
                        .passwordhash(passwordEncoder.encode("johndoe1"))
                        .phonenumber("+3123456781")
                        .email("john.doe1@example.com")
                        .address("1231 Main St")
                        .role(Role.User)
                        .build(),
                UserEntity.builder()
                        .name("arielclarence")
                        .username("arielclarence")
                        .passwordhash(passwordEncoder.encode("@aCT190902"))
                        .phonenumber("+310684318401")
                        .email("ariel.clarence@example.com")
                        .address("grave")
                        .role(Role.User)
                        .build(),
                UserEntity.builder()
                        .name("admin")
                        .username("admin")
                        .passwordhash(passwordEncoder.encode("admin"))
                        .phonenumber("+310684318401")
                        .email("admin@example.com")
                        .address("grave")
                        .role(Role.Admin)
                        .build()
        );


        if (userRepository.count() == 0) {
            userRepository.saveAll(users);
        }



    }
}
