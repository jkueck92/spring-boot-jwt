package de.jkueck.service;

import de.jkueck.database.PermissionRepository;
import de.jkueck.database.RoleRepository;
import de.jkueck.database.entities.Permission;
import de.jkueck.database.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public Permission save(Permission permission) {
        return this.permissionRepository.save(permission);
    }

}
