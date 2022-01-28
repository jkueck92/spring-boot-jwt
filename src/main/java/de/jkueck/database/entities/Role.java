package de.jkueck.database.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity implements GrantedAuthority {

    public static final String ROLE_USER = "USER";

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="role_permissions",
            joinColumns=@JoinColumn(name="role_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="permission_id", referencedColumnName="id"))
    private List<Permission> permissions = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
