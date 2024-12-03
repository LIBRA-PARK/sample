package com.practice.sample.service;

import com.practice.sample.dto.ProductExtendedDTO;
import com.practice.sample.dto.ProductSimpleDTO;
import com.practice.sample.entity.Product;
import com.practice.sample.repository.ProductRepository;
import com.practice.sample.vo.ProductVO;
import com.querydsl.core.types.Projections;
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
        ProductVO productVO = entityService.readProductById(product.getId(), product ->
                Projections.constructor(
                        ProductVO.class,
                        product.id,
                        product.name
                ));
        ProductSimpleDTO result = new ProductSimpleDTO(productVO.getId(), productVO.getName());

        assertNotNull(result);
        assertEquals(product.getId(), result.id());
        assertEquals(product.getName(), result.name());
    }

    @Test
    public void testExtendedDTOConversion() {
        ProductVO productVO = entityService.readProductById(product.getId(), product -> Projections.constructor(
                ProductVO.class,
                product.id,
                product.name,
                product.description,
                product.version
        ));
        ProductExtendedDTO result = new ProductExtendedDTO(productVO.getId(), productVO.getName(), productVO.getDescription(), productVO.getVersion());

        assertNotNull(result);
        assertEquals(product.getId(), result.id());
        assertEquals(product.getDescription(), result.description());
    }
}