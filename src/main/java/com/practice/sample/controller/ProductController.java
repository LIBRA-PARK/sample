package com.practice.sample.controller;

import com.practice.sample.dto.ProductExtendedDTO;
import com.practice.sample.dto.ProductSimpleDTO;
import com.practice.sample.service.ProductReadService;
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
        ProductSimpleDTO response = productReadService.readProductById(id, vo ->
                new ProductSimpleDTO(vo.getId(), vo.getDescription()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/extended")
    public ResponseEntity<ProductExtendedDTO> getExtended(@PathVariable Long id) {
        ProductExtendedDTO response = productReadService.readProductById(id, vo ->
                new ProductExtendedDTO(vo.getId(), vo.getName(), vo.getDescription(), vo.getVersion()));

        return ResponseEntity.ok(response);
    }
}
