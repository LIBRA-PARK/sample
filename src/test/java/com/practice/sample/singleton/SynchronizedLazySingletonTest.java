package com.practice.sample.singleton;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedLazySingletonTest {
    @RepeatedTest(10)
    public void SYNCHRONIZED_성능_비교_테스트() throws InterruptedException {
        int threadCount = 100;
        long start = System.nanoTime();
        testSingletonPerformance(SynchronizedLazySingleton::getInstance, threadCount);
        long end = System.nanoTime();
        System.out.println("Double Checked Locking Singleton Time: " + (end - start) + " ns");
    }

    private static void testSingletonPerformance(Runnable singletonMethod, int threadCount) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(singletonMethod);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}