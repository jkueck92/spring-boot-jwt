package de.jkueck.service;

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

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String login(String email, String password) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return this.jwtTokenProvider.createJWTToken(this.userRepository.findByEmail(email));
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String register(User user) {
        if (!this.userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
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
