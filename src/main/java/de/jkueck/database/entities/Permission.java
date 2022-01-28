package de.jkueck.database.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {

    private String name;

    public Permission(String name) {
        this.name = name;
    }

}
