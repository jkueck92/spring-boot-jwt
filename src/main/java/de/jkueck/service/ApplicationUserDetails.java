package de.jkueck.service;

import de.jkueck.database.entities.User;
import de.jkueck.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(s);

        if (user != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(user.getUserRoles())
                    .accountExpired(Boolean.FALSE)
                    .accountLocked(Boolean.FALSE)
                    .credentialsExpired(Boolean.FALSE)
                    .disabled(Boolean.FALSE)
                    .build();
        }
        throw new UsernameNotFoundException("User '" + s + "' not found");

    }

}
