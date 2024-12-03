package com.practice.sample.repository.custom;

import com.practice.sample.entity.QProduct;
import com.querydsl.core.types.ConstructorExpression;

@FunctionalInterface
public interface QueryProjection<T> {
    ConstructorExpression<T> apply(QProduct product);
}
