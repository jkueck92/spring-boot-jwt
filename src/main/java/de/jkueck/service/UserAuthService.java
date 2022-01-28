package de.jkueck.service;

import de.jkueck.database.UserRepository;
import de.jkueck.database.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(s);

        if (user != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(user.getRole())
                    .accountExpired(Boolean.FALSE)
                    .accountLocked(Boolean.FALSE)
                    .credentialsExpired(Boolean.FALSE)
                    .disabled(Boolean.FALSE)
                    .build();
        }
        throw new UsernameNotFoundException("User '" + s + "' not found");
    }

}
