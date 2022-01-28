package de.jkueck;

import com.github.javafaker.Faker;
import de.jkueck.database.PermissionRepository;
import de.jkueck.database.entities.Permission;
import de.jkueck.database.entities.Role;
import de.jkueck.database.entities.User;
import de.jkueck.database.entities.UserRole;
import de.jkueck.service.AuthenticationService;
import de.jkueck.service.PermissionService;
import de.jkueck.service.RoleService;
import de.jkueck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtAuthServiceApp implements CommandLineRunner {

    final RoleService roleService;
    final UserService userService;
    final AuthenticationService authenticationService;
    final PermissionService permissionService;

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthServiceApp.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... params) throws Exception {

        Permission menuItemHeaderX1Permission = this.permissionService.save(new Permission("menu_item_header_x1"));
        Permission menuItemHeaderX2Permission = this.permissionService.save(new Permission("menu_item_header_x2"));
        Permission menuItemHeaderX3Permission = this.permissionService.save(new Permission("menu_item_header_x3"));

        Role adminRole = new Role("ADMIN");
        adminRole.getPermissions().add(menuItemHeaderX1Permission);
        adminRole.getPermissions().add(menuItemHeaderX2Permission);

        Role newAdminRole = this.roleService.save(adminRole);

        Role userRole = new Role(Role.ROLE_USER);
        this.roleService.save(userRole);

        Role superAdminRole = new Role("SUPER_ADMIN");
        superAdminRole.getPermissions().add(menuItemHeaderX1Permission);
        superAdminRole.getPermissions().add(menuItemHeaderX2Permission);
        superAdminRole.getPermissions().add(menuItemHeaderX3Permission);

        Role newSuperAdminRole = this.roleService.save(superAdminRole);

        Faker faker = new Faker();

        User admin = new User();
        admin.setFirstName(faker.name().firstName());
        admin.setLastName(faker.name().lastName());
        admin.setPassword("admin");
        admin.setEmail("admin@email.com");
        admin.setRole(newAdminRole);
        this.authenticationService.register(admin);

        User superAdmin = new User();
        superAdmin.setFirstName(faker.name().firstName());
        superAdmin.setLastName(faker.name().lastName());
        superAdmin.setPassword("superadmin");
        superAdmin.setEmail("superadmin@email.com");
        superAdmin.setRole(newSuperAdminRole);
        this.authenticationService.register(superAdmin);

        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPassword("user");
        user.setEmail("user@email.com");
        this.authenticationService.register(user);


    }

}
