package de.jkueck.service;

import de.jkueck.LoginResponse;
import de.jkueck.PermissionResponse;
import de.jkueck.RoleResponse;
import de.jkueck.UserResponse;
import de.jkueck.database.entities.Permission;
import de.jkueck.database.entities.Role;
import de.jkueck.database.entities.User;
import de.jkueck.database.UserRepository;
import lombok.RequiredArgsConstructor;
import de.jkueck.exception.CustomException;
import de.jkueck.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    public LoginResponse login(String email, String password) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            User user = this.userRepository.findByEmail(email);

            String token = this.jwtTokenProvider.createJWTToken(user);

            LoginResponse loginResponse = new LoginResponse();

            loginResponse.setToken(token);

            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(user.getRole().getId());
            roleResponse.setName(user.getRole().getName());
            for (Permission permission : user.getRole().getPermissions()) {
                PermissionResponse permissionResponse = new PermissionResponse();
                permissionResponse.setId(permission.getId());
                permissionResponse.setName(permission.getName());
                roleResponse.getPermissions().add(permissionResponse);
            }

            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(roleResponse);

            loginResponse.setUser(userResponse);

            return loginResponse;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String register(User user) {
        if (!this.userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            if (user.getRole() == null) {
                user.setRole(this.roleService.getStandardUserRole());
            }
            this.userRepository.save(user);
            return this.jwtTokenProvider.createJWTToken(user);
        } else {
            throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String refresh(String email) {
        return this.jwtTokenProvider.createJWTToken(this.userRepository.findByEmail(email));
    }

}
