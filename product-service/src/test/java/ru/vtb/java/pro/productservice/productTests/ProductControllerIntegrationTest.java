package ru.vtb.java.pro.productservice.productTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.java.pro.productservice.domain.Product;
import ru.vtb.java.pro.productservice.domain.User;
import ru.vtb.java.pro.productservice.enums.ProductType;
import ru.vtb.java.pro.productservice.repository.ProductRepository;
import ru.vtb.java.pro.productservice.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Product product;
    private User user;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
        userRepository.deleteAll();

        user = new User();
        user.setUsername("John Doe");
        user = userRepository.save(user);

        product = new Product();
        product.setAccount("1234567890");
        product.setAmount(1000.50);
        product.setProductType(ProductType.CARD);
        product.setUser(user);
        product = productRepository.save(product);
    }

    @Test
    void findProductByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.account").value("1234567890"))
                .andExpect(jsonPath("$.amount").value(1000.50))
                .andExpect(jsonPath("$.productType").value(ProductType.CARD.name()));
    }

    @Test
    void findProductByUserIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/products/by-user/{userId}", user.getId())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[0].account").value("1234567890"))
                .andExpect(jsonPath("$.content[0].amount").value(1000.50))
                .andExpect(jsonPath("$.content[0].productType").value(ProductType.CARD.name()));
    }
}
