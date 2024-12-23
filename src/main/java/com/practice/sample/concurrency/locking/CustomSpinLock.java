package com.practice.sample.concurrency.locking;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSpinLock {
    private final AtomicBoolean isLocking = new AtomicBoolean(false);

    public void lock() {
        while (!isLocking.compareAndSet(false, true)) {

        }
    }

    public void unlock() {
        isLocking.set(false);
    }
}
