package com.practice.sample.concurrency.volatiles;

import lombok.Getter;

@Getter
public class Calculator {
    private int value;

    public Calculator() {
        this.value = 0;
    }

    public synchronized void plus() {
        this.value++;
    }

    public synchronized void minus() {
        this.value--;
    }
}
