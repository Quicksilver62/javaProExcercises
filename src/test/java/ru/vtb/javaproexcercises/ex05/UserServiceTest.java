package ru.vtb.javaproexcercises.ex05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.vtb.javaproexcercises.ex05.dto.UserDto;
import ru.vtb.javaproexcercises.ex05.services.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach

    void setUp() {
        jdbcTemplate.execute("truncate table users restart identity");
        jdbcTemplate.execute("""
            insert into users (username) values
                                             ('John Doe'),
                                             ('Richard Roe'),
                                             ('Jane Doe');
        """);
    }

    @Test
    public void createUserTest() {
        String username = "Rachel Roe";
        UserDto userDto = new UserDto(null, username);

        userService.createUser(userDto);

        assertThat(userService.findAllUsers())
                .anyMatch(u -> u.username().equals(username));
    }

    @Test
    public void deleteUserTest() {
        UserDto user = userService.findAllUsers().getFirst();
        userService.deleteUser(user);
        assertThat(userService.findAllUsers())
                .noneMatch(u -> u.username().equals(user.username()));
    }

    @Test
    public void findUserByIdTest() {
        UserDto user = userService.findUserById(1L);
        assertThat(user).isNotNull()
                .extracting("id").isEqualTo(1L);
    }

    @Test
    public void findAllUsersTest() {
        List<UserDto> users = userService.findAllUsers();
        assertThat(users).hasSize(3);
    }
}
