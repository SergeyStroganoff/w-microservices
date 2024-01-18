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
import lombok.experimental.PackagePrivate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@PackagePrivate
public class UserServiceIml implements UserService, UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceIml.class);
    public static final String ERROR_DELETING_USER_WITH_USER_NAME = "Error deleting user with user name: ";
    private static final String USER_DOES_T_HAVE_WAREHOUSE = "User warehouse list is empty. UserName: %s";
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier("feign-client")
    private WarehouseService warehouseService;

    @Autowired
    // SessionFactory is used to get a session from the database - see nativeSQLGetUserByWarehouseIdQuery method
    private SessionFactory sessionFactory;

    public UserServiceIml() {
    }

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
    public String save(UserDTO userDTO, int warehouseId, String token) throws RepositoryTransactionException, ServiceValidationException, MicroserviceCommunicationException {
        if (userRepository.findUserByUserName(userDTO.getUserName()).isPresent()) { // TODO
            throw new ServiceValidationException("User with the same name exists!");
        }
        if (!warehouseService.warehouseExist(warehouseId, token)) {
            throw new ServiceValidationException("warehouse id is not valid or warehouse-service is not available");
        }
        User user = modelMapper.map(userDTO, User.class);
        if (user.getWarehouseList() == null) {
            user.setWarehouseList(new ArrayList<>());
        }
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
    /**
     * This method is used to get all users (except user with userName)
     * from the database
     * with the same warehouses as the user with the userName
     */
    public List<UserDTO> getAllConnectedUsers(String userName) {
        Optional<User> userOptional = userRepository.findUserByUserName(userName);
        if (userOptional.isEmpty()) {
            String message = String.format("There is no user: %s in database", userName);
            logger.error(message);
            throw new UsernameNotFoundException("There is no user: " + userName + " in database");
        }
        User userFromDataBase = userOptional.get();
        if (userFromDataBase.getWarehouseList().isEmpty()) {
            logger.warn(USER_DOES_T_HAVE_WAREHOUSE);
            return Collections.emptyList();
        }
        List<String> userNameList = userFromDataBase.getWarehouseList().stream()
                .flatMap(x -> nativeSQLGetUserByWarehouseIdQuery(x.getId()).stream()).toList();
        List<User> userList = userRepository.findAllById(userNameList);
        // List<User> userList = userFromDataBase.getWarehouseList().stream()
        // .flatMap(x -> x.getUserList().stream()).toList();
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

    private List<String> nativeSQLGetUserByWarehouseIdQuery(int warehouseId) {
        List<String> userNameList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                String sqlQuery = "SELECT username FROM user_warehouse WHERE warehouse_id = :warehouseId";
                Query<String> query = session.createNativeQuery(sqlQuery, String.class)
                        .setParameter("warehouseId", warehouseId);
                // Execute the query
                userNameList = query.list();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return userNameList;
    }
}
