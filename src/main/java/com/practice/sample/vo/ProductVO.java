package com.practice.sample.vo;

import lombok.Getter;

@Getter
public class ProductVO {
    private final Long id;
    private final String name;
    private final String description;
    private final int version;

    public ProductVO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.version = builder.version;
    }

    public static class Builder {
        private final Long id;
        private final String name;
        private String description;
        private int version;

        public Builder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        public ProductVO build() {
            return new ProductVO(this);
        }
    }
}
