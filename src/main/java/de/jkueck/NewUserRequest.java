package de.jkueck;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
