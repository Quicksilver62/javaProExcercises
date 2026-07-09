package ru.vtb.java.pro.productservice.userTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.vtb.java.pro.productservice.controller.UserController;
import ru.vtb.java.pro.productservice.dto.UserDto;
import ru.vtb.java.pro.productservice.services.UserService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void findUserByIdTest() throws Exception {
        UserDto userDto = new UserDto(1L, "John Doe", null);
        when(userService.findUserById(userDto.id())).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/users/{id}", userDto.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.products").doesNotExist());

        verify(userService, times(1)).findUserById(userDto.id());
    }

    @Test
    void findAllUsersTest() throws Exception {
        List<UserDto> userList = List.of(
                new UserDto(1L, "John Doe", null),
                new UserDto(2L, "Richard Roe", null)
        );
        Page<UserDto> userPage = new PageImpl<>(userList);

        when(userService.findAllUsers(any(Pageable.class))).thenReturn(userPage);

        mockMvc.perform(get("/api/v1/users/")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].username").value("John Doe"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].username").value("Richard Roe"));

        verify(userService, times(1)).findAllUsers(any(Pageable.class));
    }

    @Test
    void createUserTest() throws Exception {
        UserDto userDto = new UserDto(1L, "John Doe", null);
        doNothing().when(userService).createUser(any(UserDto.class));

        mockMvc.perform(post("/api/v1/users/create", userDto))
                .andExpect(status().isCreated());

        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void deleteUserTest() throws Exception {
        UserDto userDto = new UserDto(1L, "John Doe", null);
        doNothing().when(userService).deleteUser(any(UserDto.class));

        mockMvc.perform(delete("/api/v1/users/delete", userDto))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(any(UserDto.class));
    }
}
