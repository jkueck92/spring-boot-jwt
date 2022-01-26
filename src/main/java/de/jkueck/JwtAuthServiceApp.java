package de.jkueck;

import java.util.ArrayList;
import java.util.Collections;

import com.github.javafaker.Faker;
import de.jkueck.database.entities.User;
import de.jkueck.database.entities.UserRole;
import de.jkueck.service.AuthenticationService;
import de.jkueck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtAuthServiceApp implements CommandLineRunner {

  final UserService userService;
  final AuthenticationService authenticationService;

  public static void main(String[] args) {
    SpringApplication.run(JwtAuthServiceApp.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public void run(String... params) throws Exception {

    Faker faker = new Faker();

    User admin = new User();
    admin.setFirstName(faker.name().firstName());
    admin.setLastName(faker.name().lastName());
    admin.setPassword("admin");
    admin.setEmail("admin@email.com");
    admin.setUserRoles(new ArrayList<>(Collections.singletonList(UserRole.ROLE_ADMIN)));

    this.authenticationService.register(admin);

    User client = new User();
    client.setFirstName(faker.name().firstName());
    client.setLastName(faker.name().lastName());
    client.setPassword("client");
    client.setEmail("client@email.com");
    client.setUserRoles(new ArrayList<>(Collections.singletonList(UserRole.ROLE_CLIENT)));

    this.authenticationService.register(client);
  }

}
