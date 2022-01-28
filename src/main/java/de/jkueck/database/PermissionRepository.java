package de.jkueck.database;

import de.jkueck.database.entities.Permission;
import de.jkueck.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
