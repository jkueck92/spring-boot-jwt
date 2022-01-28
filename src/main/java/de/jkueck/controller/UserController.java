package de.jkueck.controller;

import de.jkueck.UserResponse;
import de.jkueck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @DeleteMapping(value = "/{id}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long delete(@PathVariable Long id) {
        this.userService.deleteById(id);
        return id;
    }

    @GetMapping(value = "/{id}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponse search(@PathVariable Long id) {
        return this.modelMapper.map(userService.getById(id), UserResponse.class);
    }

    @GetMapping(value = "/me")
    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public UserResponse whoAmI(HttpServletRequest req) {
        return this.modelMapper.map(this.userService.whoAmI(req), UserResponse.class);
    }
}
