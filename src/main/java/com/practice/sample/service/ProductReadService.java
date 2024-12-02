package com.practice.sample.service;

import com.practice.sample.entity.Product;
import com.practice.sample.repository.ProductRepository;
import com.practice.sample.vo.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductReadService {
    private final ProductRepository productRepository;

    public ProductReadService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public <T> T readProductById(Long id, ProductMapper<T> mapper) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("엔티티를 찾을 수 없음"));
        return mapper.convert(product);
    }
}
