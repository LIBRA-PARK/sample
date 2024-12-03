package com.practice.sample.repository.custom;

import java.util.List;

public interface CustomProductRepository {
    <T> T findByIdWithVO(Long id, QueryProjection<T> projection);
    <T> List<T> findsInIdsWithVO(List<Long> ids, QueryProjection<T> projection);
}
