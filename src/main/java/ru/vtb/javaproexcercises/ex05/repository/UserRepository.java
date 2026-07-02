package ru.vtb.javaproexcercises.ex05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.javaproexcercises.ex05.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
