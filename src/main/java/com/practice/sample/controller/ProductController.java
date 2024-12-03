package com.practice.sample.controller;

import com.practice.sample.dto.ProductExtendedDTO;
import com.practice.sample.dto.ProductSimpleDTO;
import com.practice.sample.service.ProductReadService;
import com.practice.sample.vo.ProductVO;
import com.querydsl.core.types.Projections;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductReadService productReadService;

    public ProductController(ProductReadService productReadService) {
        this.productReadService = productReadService;
    }

    @GetMapping("/{id}/basic")
    public ResponseEntity<ProductSimpleDTO> getSimple(@PathVariable Long id) {
        ProductVO productVO = productReadService.readProductById(id, product ->
                Projections.constructor(
                        ProductVO.class,
                        product.id,
                        product.name
                ));

        ProductSimpleDTO response = new ProductSimpleDTO(productVO.getId(), productVO.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/extended")
    public ResponseEntity<ProductExtendedDTO> getExtended(@PathVariable Long id) {
        ProductVO productVO = productReadService.readProductById(id, product -> Projections.constructor(
                ProductVO.class,
                product.id,
                product.name,
                product.description,
                product.version
        ));

        ProductExtendedDTO response = new ProductExtendedDTO(productVO.getId(), productVO.getName(), productVO.getDescription(), productVO.getVersion());
        return ResponseEntity.ok(response);
    }
}
