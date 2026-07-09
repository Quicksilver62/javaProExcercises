package ru.vtb.java.pro.productservice.userTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.java.pro.productservice.dto.UserDto;
import ru.vtb.java.pro.productservice.repository.UserRepository;
import ru.vtb.java.pro.productservice.services.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUserTest() {
        String username = "Rachel Roe";
        UserDto userDto = new UserDto(null, username, List.of());
        userService.createUser(userDto);
        assertThat(userRepository.findAll())
                .anyMatch(u -> u.getUsername().equals(username));
    }

    @Test
    public void deleteUserTest() {
        UserDto user = userService.findAllUsers(Pageable.unpaged())
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("User not found"));
        userService.deleteUser(user);
        assertThat(userRepository.findAll())
                .noneMatch(u -> u.getUsername().equals(user.username()));
    }

    @Test
    public void findUserByIdTest() {
        UserDto user = userService.findUserById(1L);
        assertThat(user).isNotNull()
                .extracting("id").isEqualTo(1L);
    }

    @Test
    public void findAllUsersTest() {
        Page<UserDto> users = userService.findAllUsers(Pageable.unpaged());
        assertThat(users).hasSize(3);
    }
}
