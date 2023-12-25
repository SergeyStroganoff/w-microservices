package com.stroganov.service;

import com.stroganov.domain.dto.user.UserDTO;
import com.stroganov.domain.model.user.Authorities;
import com.stroganov.domain.model.user.User;
import com.stroganov.domain.model.warehouse.Warehouse;
import com.stroganov.exception.MicroserviceCommunicationException;
import com.stroganov.exception.RepositoryTransactionException;
import com.stroganov.exception.ServiceValidationException;
import com.stroganov.exception.UserNotFoundException;
import com.stroganov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@PackagePrivate
public class UserServiceIml implements UserService, UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceIml.class);
    public static final String ERROR_DELETING_USER_WITH_USER_NAME = "Error deleting user with user name: ";
    public static final String UNAUTHORIZED_SESSION_MESSAGE = "Trying to get a user from an unauthorized session";
    private static final String USER_DOES_T_HAVE_WAREHOUSE = "User warehouse list is empty. UserName: %s";
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WarehouseService warehouseService;

    @Override
    @Transactional(readOnly = true)
    /**
     User DetailService method
     */
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with userName: " + userName + " not found !"));
    }

    @Transactional
    public String save(UserDTO userDTO, int warehouseId) throws RepositoryTransactionException, ServiceValidationException, MicroserviceCommunicationException {
        if (userRepository.findUserByUserName(userDTO.getUserName()).isPresent()) {
            throw new ServiceValidationException("User with the same name exists!");
        }
        if (!warehouseService.warehouseExist(warehouseId)) {
            throw new ServiceValidationException("warehouse id is not valid");
        }
        User user = modelMapper.map(userDTO, User.class);
        String userPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(userPassword);
        user.setPassword(encodedPassword);
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        user.getWarehouseList().add(warehouse);
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error saving user with user name: " + user.getUsername(), e);
            throw new RepositoryTransactionException("Error saving user with user name: " + user.getUsername(), e);
        }
        return user.getUsername();
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) throws RepositoryTransactionException {
        if (userRepository.findUserByUserName(userDTO.getUserName()).isEmpty()) {
            throw new UsernameNotFoundException("User with the  name: " + userDTO.getUserName() + " was not found");
        }
        User user = modelMapper.map(userDTO, User.class);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        try {
            userRepository.update(encodedPassword, user.getFullName(), user.getEmail(), user.isEnabled(), (Set<Authorities>) user.getAuthorities(), userDTO.getUserName());
        } catch (Exception e) {
            logger.error("Error updating user with user name: " + userDTO.getUserName(), e);
            throw new RepositoryTransactionException("Error updating user with user name: " + userDTO.getUserName(), e);
        }
    }

    @Transactional
    public String delete(String userName) throws RepositoryTransactionException {
        User user = userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + userName + " not found !"));
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            logger.error(ERROR_DELETING_USER_WITH_USER_NAME + userName, e);
            throw new RepositoryTransactionException(ERROR_DELETING_USER_WITH_USER_NAME + user.getUsername(), e);
        }
        return userName;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserDTOByName(String userName) {
        Optional<User> userOptional = userRepository.findById(userName);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        UserDTO userDTO = modelMapper.map(userOptional.get(), UserDTO.class);
        return Optional.of(userDTO);
    }

    @Override
    public Optional<User> findUserByName(String userName) {
        return userRepository.findById(userName);
    }


    @Override
    @Transactional
    public List<UserDTO> getAllConnectedUsers(String userName) {
        Optional<User> userOptional = userRepository.findUserByUserName(userName);
        if (userOptional.isEmpty()) {
            logger.error("There is no user: " + userName + " in database, - it is unlivable!");
            throw new RuntimeException("There is no user: " + userName + " in database");
        }
        User userFromDataBase = userOptional.get();
        if (userFromDataBase.getWarehouseList().isEmpty()) {
            logger.warn(USER_DOES_T_HAVE_WAREHOUSE, userFromDataBase);
            return Collections.emptyList();
        }

        List<User> userList = userFromDataBase.getWarehouseList().stream()
                .flatMap(x -> x.getUserList().stream()).toList();
        List<UserDTO> userDTOList = modelMapper.map(userList, new TypeToken<List<UserDTO>>() {
        }.getType());
        return userDTOList.stream().filter(x -> !x.getUserName().equals(userName)).toList();
    }

    @Override
    @Transactional
    public void changeUserStatus(String userName) throws UserNotFoundException {
        User user = userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + userName + " not found !"));
        user.setEnabled(!user.isEnabled());
        userRepository.flush();
    }
}
