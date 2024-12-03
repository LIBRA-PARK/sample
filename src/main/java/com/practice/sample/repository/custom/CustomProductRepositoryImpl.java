package com.practice.sample.repository.custom;

import com.practice.sample.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class CustomProductRepositoryImpl implements CustomProductRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public CustomProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public <T> T findByIdWithVO(Long id, QueryProjection<T> projection) {
        QProduct product = QProduct.product;
        return jpaQueryFactory
                .select(projection.apply(product))
                .from(product)
                .where(product.id.eq(id))
                .fetchOne();
    }

    @Override
    public <T> List<T> findsInIdsWithVO(List<Long> ids, QueryProjection<T> projection) {
        QProduct product = QProduct.product;
        return jpaQueryFactory
                .select(projection.apply(product))
                .from(product)
                .where(product.id.in(ids))
                .fetch();
    }
}
