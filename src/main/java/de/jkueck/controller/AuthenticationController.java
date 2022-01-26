package de.jkueck.controller;

import de.jkueck.service.AuthenticationService;
import de.jkueck.database.entities.User;
import de.jkueck.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return this.authenticationService.login(email, password);
    }

    @PostMapping("/register")
    public String signup(@RequestBody UserDataDto user) {
        return this.authenticationService.register(this.modelMapper.map(user, User.class));
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return this.authenticationService.refresh(req.getRemoteUser());
    }

}
