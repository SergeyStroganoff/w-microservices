package com.stroganov.controller;

import com.stroganov.domain.model.user.User;
import com.stroganov.exception.UserNotFoundException;
import com.stroganov.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "User Controller", description = "User management APIs")
@RestController
@RequestMapping("/api/users")
@RefreshScope //
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{name}")
    public User getUserByName(@PathVariable @NotBlank String name) throws UserNotFoundException {
        Optional<User> userOptional = userService.findUserByName(name);
        return userOptional.orElseThrow(() -> new UserNotFoundException("User with id: " + name + " not found"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/admin/") //todo
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @GetMapping("/user/") //todo
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }
}
