package com.practice.sample.service;

import com.practice.sample.dto.ProductExtendedDTO;
import com.practice.sample.dto.ProductSimpleDTO;
import com.practice.sample.entity.Product;
import com.practice.sample.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Transactional
class ProductReadServiceTest {

    @Autowired
    private ProductReadService entityService;

    @Autowired
    private ProductRepository entityRepository;

    private Product product;

    @BeforeAll
    public void setUpOnce() {
        // 공통 테스트 데이터 생성 (한 번만 실행)
        product = new Product();
        product.setName("Test Name");
        product.setDescription("Test Description");
        product.setDescription("Test Settings");
        product.setVersion(1);
        entityRepository.save(product);
    }

    @Test
    public void testProductSimpleDTOConversion() {
        ProductSimpleDTO result = entityService.readProductById(product.getId(),
                e -> new ProductSimpleDTO(e.getId(), e.getName()));

        assertNotNull(result);
        assertEquals(product.getId(), result.id());
        assertEquals(product.getName(), result.name());
    }

    @Test
    public void testExtendedDTOConversion() {
        ProductExtendedDTO result = entityService.readProductById(product.getId(),
                e -> new ProductExtendedDTO(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getVersion()
                ));

        assertNotNull(result);
        assertEquals(product.getId(), result.id());
        assertEquals(product.getDescription(), result.description());
    }
}