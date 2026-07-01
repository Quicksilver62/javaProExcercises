package ru.vtb.javaproexcercises.ex04;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.javaproexcercises.ex04.dao.UserDao;
import ru.vtb.javaproexcercises.ex04.domain.User;
import ru.vtb.javaproexcercises.ex04.services.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory factory;

    @Test
    public void createUserTest() {
        String username = "John Doe";
        UserDao userDao = new UserDao(null, username);

        userService.createUser(userDao);

        try(Session session = factory.openSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).list();

            assertThat(users)
                    .hasSize(1)
                    .allMatch(u -> u.getUsername().equals(username));

            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteUserTest() {
        String username = "John Doe";
        User user = new User();
        user.setUsername(username);

        Long userId;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            User dbUser = session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            userId = dbUser.getId();
            session.getTransaction().commit();
        }

        UserDao userToDelete = new UserDao(userId, username);
        userService.deleteUser(userToDelete);

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<User> usersAfter = session.createQuery("from User", User.class).list();
            assertThat(usersAfter).isEmpty();
            session.getTransaction().commit();
        }
    }

    @Test
    public void findUserByIdTest() {
        String username = "John Doe";
        User user = new User();
        user.setUsername(username);

        Long userId;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            User dbUser = session.createQuery("from User", User.class).getSingleResult();
            userId = dbUser.getId();
            session.getTransaction().commit();
        }

        UserDao foundUser = userService.findUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.id());
        assertEquals(username, foundUser.username());
    }

    @Test
    public void findAllUsersTest() {
        UserDao user1 = new UserDao(null, "John Doe");
        UserDao user2 = new UserDao(null, "Richard Roe");
        UserDao user3 = new UserDao(null, "Jane Doe");

        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        List<UserDao> allUsers = userService.findAllUsers();

        assertThat(allUsers)
                .hasSize(3)
                .extracting(UserDao::username)
                .containsExactlyInAnyOrder("John Doe", "Richard Roe", "Jane Doe");
    }
}
