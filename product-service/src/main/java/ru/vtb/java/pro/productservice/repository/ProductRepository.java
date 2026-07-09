package ru.vtb.java.pro.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.java.pro.productservice.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByUserId(Long userId, Pageable pageable);
}
