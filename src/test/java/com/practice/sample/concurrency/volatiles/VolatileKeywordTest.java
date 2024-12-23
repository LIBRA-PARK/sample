package com.practice.sample.concurrency.volatiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class VolatileKeywordTest {
    private static final Logger log = LoggerFactory.getLogger(VolatileKeywordTest.class);
    private static final int NUM_THREADS = 10; // 스레드 개수
    private static final int NUM_ITERATIONS = 1_000_000; // 반복 횟수

    @Test
    @DisplayName("메모리 가시성 문제")
    void run() throws InterruptedException {
        // Given
        VolatileKeyword volatileKeyword = new VolatileKeyword();

        // Create a thread with the VolatileKeyword instance
        Thread thread = new Thread(volatileKeyword);

        // When
        thread.start(); // Start the thread, which will enter an infinite loop

        // Let the thread run for a short time
        Thread.sleep(100);

        // Change the isRunning flag to false
        volatileKeyword.setRunning(false);

        // Wait for the thread to terminate
        thread.join(500); // Wait up to 500ms for the thread to finish

        // Then
        // If the thread terminated, it means the memory visibility worked.
        // If the thread did not terminate, it demonstrates a visibility issue.
        assertFalse(thread.isAlive(), "Thread should have terminated, but it is still running due to memory visibility issues.");
    }

    @Test
    @DisplayName("메모리 동시성 문제")
    void WRITE_동시성_문제() throws InterruptedException {
        // Given
        Calculator calculator = new Calculator();
        int loopCount = 20_000;
        int threadCount = 2;

        // Thread pool with two threads
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // When
        for (int i = 0; i < loopCount / 2; i++) {
            executor.execute(calculator::plus);
            executor.execute(calculator::minus);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Then
        // Expecting value to be 0
        assertEquals(0, calculator.getValue(),
                "Expected value to be 0, but found " + calculator.getValue() +
                        ". This indicates a concurrency issue.");
    }
    
    @Test
    @DisplayName("synchronized 블로과 spinlock 소요시간 비교")
    void LOCK_비교() {
        Calculator calculator = new Calculator();
        CalculatorWithSpinLock calculatorWithSpinLock = new CalculatorWithSpinLock();

        long synchronizedTime = measureExecutionTime(() ->
                runConcurrentTest(() -> {
                    calculator.plus();
                    calculator.minus();
                })
        );

        long spinLockTime = measureExecutionTime(() ->
                runConcurrentTest(() -> {
                    calculatorWithSpinLock.plus();
                    calculatorWithSpinLock.minus();
                })
        );

        log.info("Synchronized Calculator execution time: {} ns", synchronizedTime);
        log.info("SpinLock-based Calculator execution time: {} ns", spinLockTime);

        // 두 Calculator의 최종 값이 동일한지 확인
        assertEquals(0, calculator.getValue());
        assertEquals(0, calculatorWithSpinLock.getValue());
    }

    private void runConcurrentTest(Runnable task) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch latch = new CountDownLatch(NUM_THREADS); // 스레드 개수만큼 초기화

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < NUM_ITERATIONS / NUM_THREADS; j++) {
                        task.run();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        }

        executor.shutdown();
    }

    private long measureExecutionTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}