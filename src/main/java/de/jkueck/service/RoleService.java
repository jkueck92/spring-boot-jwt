package de.jkueck.service;

import de.jkueck.database.RoleRepository;
import de.jkueck.database.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getStandardUserRole() {
        Optional<Role> optionalRole = this.roleRepository.findByName(Role.ROLE_USER);
        return optionalRole.orElse(null);
    }

    public Role save(Role role) {
        return this.roleRepository.save(role);
    }

}
