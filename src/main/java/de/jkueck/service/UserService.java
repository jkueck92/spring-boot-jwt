package de.jkueck.service;

import de.jkueck.database.entities.User;
import de.jkueck.database.UserRepository;
import lombok.RequiredArgsConstructor;
import de.jkueck.exception.CustomException;
import de.jkueck.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    public User whoAmI(HttpServletRequest req) {
        return this.userRepository.findByEmail(this.jwtTokenProvider.getUsername(this.jwtTokenProvider.resolveToken(req)));
    }

}
