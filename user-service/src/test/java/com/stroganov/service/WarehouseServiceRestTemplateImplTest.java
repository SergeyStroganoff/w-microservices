package com.stroganov.service;

import com.stroganov.exception.MicroserviceCommunicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

class WarehouseServiceRestTemplateImplTest {

    private final JwtService jwtService = new JwtService();

    //@Test
//    void ifWarehouseExistThenReturnTrue() throws MicroserviceCommunicationException {
//        //given
//        WarehouseService warehouseService = new WarehouseServiceRestTemplateImpl();
//        int warehouseId = 1;
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ADMIN");
//        jwtService.setSECRET("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
//        String jwtToken = jwtService.generateToken("Linas", List.of(grantedAuthority));
//        //when
//        boolean isExist = warehouseService.warehouseExist(warehouseId, jwtToken);
//        //then
//        Assertions.assertTrue(isExist);
//    }
}