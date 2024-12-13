package com.practice.sample.thread.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentTest {
    @Test
    @DisplayName("countDownLatch 테스트")
    void LATCH_TEST() throws InterruptedException {
        //given
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //when
        for (int i = 1; i <= 100; i++) {
            final int index = i;
            executor.execute(() -> {
                System.out.println(index);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        // then
        System.out.println("success CountDown");
    }

    @Test
    @DisplayName("countDownLatch 없이 테스트")
    void WITHOUT_LATCH_TEST() throws InterruptedException {
        //given0);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //when
        for (int i = 1; i <= 100; i++) {
            final int index = i;
            executor.execute(() -> {
                System.out.println(index);
            });
        }

        // then
        System.out.println("success CountDown");
    }
}