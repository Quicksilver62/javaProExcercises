package ru.vtb.javaproexcercises.ex04.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.vtb.javaproexcercises.ex04.dao.UserDao;
import ru.vtb.javaproexcercises.ex04.mappers.UserMapper;
import ru.vtb.javaproexcercises.ex04.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final SessionFactory factory;

    public void createUser(UserDao userDao) {
        User user = userMapper.toEntity(userDao);

        try(Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    public void deleteUser(UserDao userDao) {
        try(Session session = factory.openSession()) {
            session.beginTransaction();
            User user = session.find(User.class, userDao.id());

            if (user != null) {
                session.remove(user);
            }
            session.getTransaction().commit();
        }
    }

    public UserDao findUserById(Long id) {
        try(Session session = factory.openSession()) {
            session.beginTransaction();
            var user = session.find(User.class, id);
            session.getTransaction().commit();

            return userMapper.toDao(user);
        }
    }

    public List<UserDao> findAllUsers() {
        try(Session session = factory.openSession()) {
            session.beginTransaction();
            var users = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();

            return users.stream()
                    .map(userMapper::toDao)
                    .collect(Collectors.toList());
        }
    }
}
