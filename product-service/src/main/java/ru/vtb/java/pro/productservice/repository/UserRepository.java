package ru.vtb.java.pro.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.java.pro.productservice.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
