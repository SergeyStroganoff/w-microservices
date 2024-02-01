package com.stroganov.controller;

import com.stroganov.domain.dto.user.UserDTO;
import com.stroganov.domain.model.user.User;
import com.stroganov.exception.MicroserviceCommunicationException;
import com.stroganov.exception.RepositoryTransactionException;
import com.stroganov.exception.ServiceValidationException;
import com.stroganov.exception.UserNotFoundException;
import com.stroganov.mq.MessageProducer;
import com.stroganov.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User Controller", description = "User management APIs")
@RestController
@RequestMapping("/api/users")
@RefreshScope //
@Validated
public class UserController {

    public static final String WELCOME_TO_THE_USER_API = "Welcome to the user API";
    private final UserService userService;
    private final MessageProducer messageProducer;

    public UserController(UserService userService, MessageProducer messageProducer) {
        this.userService = userService;
        this.messageProducer = messageProducer;
    }

    @GetMapping("/{name}")
    public User getUserByName(@PathVariable @NotBlank String name) throws UserNotFoundException {
        Optional<User> userOptional = userService.findUserByName(name);
        return userOptional.orElseThrow(() -> new UserNotFoundException("User with id: " + name + " not found"));
    }

    @GetMapping("/same/{name}")
    public List<UserDTO> getUsersWithSameWarehouses(@PathVariable @NotBlank String name)  {
        return userService.getAllConnectedUsers(name);
    }

    @GetMapping("/welcome")
    public String welcome() {
        messageProducer.sendMessage(WELCOME_TO_THE_USER_API);
        return WELCOME_TO_THE_USER_API;
    }

    @PostMapping("/{warehouseId}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveNewUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int warehouseId, @RequestBody UserDTO userDTO) throws RepositoryTransactionException, ServiceValidationException, MicroserviceCommunicationException {
         return userService.save(userDTO, warehouseId, authorizationHeader);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable String name) throws RepositoryTransactionException {
        return userService.delete(name);
    }
}
