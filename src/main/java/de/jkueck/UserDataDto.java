package de.jkueck;

import de.jkueck.database.entities.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDataDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<UserRole> userRoles;

}
