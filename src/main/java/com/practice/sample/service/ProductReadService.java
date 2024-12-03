package com.practice.sample.service;

import com.practice.sample.repository.ProductRepository;
import com.practice.sample.repository.custom.QueryProjection;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReadService {
    private final ProductRepository productRepository;

    public ProductReadService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    public <T> T readProductById(Long id, ProductMapper<T> mapper) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("엔티티를 찾을 수 없음"));
//        return mapper.convert(product);
//    }

    public <T> T readProductById(Long id, QueryProjection<T> projection) {
        // Null 체크 및 예외 처리
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        T result = productRepository.findByIdWithVO(id, projection);
        if (result == null) {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }

        return result;
    }

    public <T> List<T> readProductsInIds(List<Long> ids, QueryProjection<T> projection) {
        // Null 체크 및 빈 리스트 처리
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ID list must not be null or empty");
        }

        List<T> results = productRepository.findsInIdsWithVO(ids, projection);
        if (results.isEmpty()) {
            return List.of();
        }
        return results;
    }
}
