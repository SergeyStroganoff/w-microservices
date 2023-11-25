package com.stroganov.domain.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationDTO extends UserDTO {
    private String warehouseName;
    private String address;

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "userName=" + this.getUserName() + '\'' +
                "fullName=" + this.getFullName() + '\'' +
                "password=" + this.getPassword() + '\'' +
                "Address=" + this.getAddress() + '\'' +
                "Email=" + this.getEmail() + '\'' +
                "warehouseName='" + warehouseName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
