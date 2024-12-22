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
}