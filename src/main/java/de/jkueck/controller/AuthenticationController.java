package de.jkueck.controller;

import de.jkueck.LoginRequest;
import de.jkueck.LoginResponse;
import de.jkueck.NewUserRequest;
import de.jkueck.service.AuthenticationService;
import de.jkueck.database.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return this.authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody NewUserRequest user) {
        return this.authenticationService.register(this.modelMapper.map(user, User.class));
    }

    @GetMapping("/refresh")
    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return this.authenticationService.refresh(req.getRemoteUser());
    }

}
