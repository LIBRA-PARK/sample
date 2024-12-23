package com.practice.sample.concurrency.volatiles;

import com.practice.sample.concurrency.locking.CustomSpinLock;
import lombok.Getter;

@Getter
public class CalculatorWithSpinLock {
    private int value;
    private final CustomSpinLock spinLock = new CustomSpinLock();

    public CalculatorWithSpinLock() {
        this.value = 0;
    }

    public void plus() {
        spinLock.lock();
        try {
            this.value++;
        } finally {
            spinLock.unlock();
        }
    }

    public void minus() {
        spinLock.lock();
        try {
            this.value--;
        } finally {
            spinLock.unlock();
        }
    }
}
