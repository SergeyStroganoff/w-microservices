package com.stroganov.service;


import com.stroganov.domain.dto.user.UserDTO;
import com.stroganov.domain.model.user.User;
import com.stroganov.exception.MicroserviceCommunicationException;
import com.stroganov.exception.RepositoryTransactionException;
import com.stroganov.exception.ServiceValidationException;
import com.stroganov.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String save(UserDTO userDTO, int warehouseId) throws RepositoryTransactionException, ServiceValidationException, MicroserviceCommunicationException;

    void update(UserDTO userDTO) throws RepositoryTransactionException;

    String delete(String userName) throws RepositoryTransactionException;

    Optional<UserDTO> getUserDTOByName(String userName);

    Optional<User> findUserByName(String userName);

    List<UserDTO> getAllConnectedUsers(String userName);

    void changeUserStatus(String userName) throws UserNotFoundException;
}
