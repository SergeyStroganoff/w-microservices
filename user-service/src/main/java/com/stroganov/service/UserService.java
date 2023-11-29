package com.stroganov.service;


import com.stroganov.domain.dto.user.UserDTO;
import com.stroganov.domain.model.user.User;
import com.stroganov.exception.RepositoryTransactionException;
import com.stroganov.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String save(UserDTO userDTO) throws RepositoryTransactionException;

    void update(UserDTO userDTO) throws RepositoryTransactionException;

    void delete(String userName) throws RepositoryTransactionException;

    Optional<UserDTO> getUserDTOByName(String userName);

    Optional<User> findUserByName(String userName);

    Object getAuthenticatedUser();

    List<UserDTO> getAllConnectedUsers(String userName);

    void changeUserStatus(String userName) throws UserNotFoundException;
}
