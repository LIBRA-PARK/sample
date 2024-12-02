package com.practice.sample.vo;

import com.practice.sample.entity.Product;

public interface ProductMapper<T> {
    T convert(Product product);
}
