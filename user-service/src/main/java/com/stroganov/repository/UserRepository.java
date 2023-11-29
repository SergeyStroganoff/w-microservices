package com.stroganov.repository;


import com.stroganov.domain.model.user.Authorities;
import com.stroganov.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUserName(String userName);

    @Transactional
    @Modifying
        //  @Query("""
        //          update User u set u.password = ?1, u.fullName = ?2, u.email = ?3, u.enabled = ?4, u.authorities = ?5
        //          where u.userName = ?6""")
    void update(String password, String fullName, String email, boolean enabled, Set<Authorities> authorities, String userName);
}
