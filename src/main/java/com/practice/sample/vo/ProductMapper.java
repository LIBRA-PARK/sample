package com.practice.sample.vo;

import com.practice.sample.entity.QProduct;

@FunctionalInterface
public interface ProductMapper<T> {
    T convert(QProduct product);
}
