package com.stroganov.domain.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String userName;
    private String password;
    private String fullName;
    private String email;
    private boolean enabled;
    @ToString.Exclude
    private Set<AuthoritiesDTO> authorities = new HashSet<>();


    public UserDTO(String userName, String password, String fullName, String email, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;

        if (enabled != userDTO.enabled) return false;
        if (!Objects.equals(userName, userDTO.userName)) return false;
        if (!Objects.equals(password, userDTO.password)) return false;
        if (!Objects.equals(fullName, userDTO.fullName)) return false;
        if (!Objects.equals(email, userDTO.email)) return false;
        return Objects.equals(authorities, userDTO.authorities);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }
}

