package com.stroganov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stroganov.domain.model.user.Authorities;
import com.stroganov.domain.model.user.Role;
import com.stroganov.domain.model.user.User;
import com.stroganov.domain.model.warehouse.Warehouse;
import com.stroganov.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserRepository userRepository;

    private final Set<Authorities> authoritiesSet = Set.of(new Authorities(Role.ROLE_USER.getRoleName()));
    private final Warehouse warehouse = new Warehouse();
    private final User user = new User("Aleks", "12345", "Aleks Fitcher", "aleks@123.com", Boolean.TRUE, authoritiesSet, List.of(warehouse));

    @Test
    void givenNotFound_whenGetSpecificException_thenNotFoundCode() throws Exception {

        String exceptionParam = "not_found";
        mvc.perform(get("/exception/{exception_id}", exceptionParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertEquals("resource not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void whenAllUsersRequest_ThenAllUsersListReturn() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of(user));
        mvc.perform((get("/users")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(List.of(user))))
                .andReturn();

        verify(userRepository).findAll();
    }
}