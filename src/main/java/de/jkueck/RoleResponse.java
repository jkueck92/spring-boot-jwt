package de.jkueck;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleResponse {

    private Long id;

    private String name;

    private List<PermissionResponse> permissions = new ArrayList<>();

}
