package com.practice.sample.singleton;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DoubleCheckedLockingSingletonTest {
    @RepeatedTest(10)
    public void VOLATILE_키워드_초기화_문제() throws ExecutionException, InterruptedException {
        int threadCount = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        Callable<DoubleCheckedLockingSingleton> task = DoubleCheckedLockingSingleton::getInstance;

        List<Future<DoubleCheckedLockingSingleton>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(task));
        }

        Set<DoubleCheckedLockingSingleton> instances = new HashSet<>();
        Set<Integer> dataValues = new HashSet<>();
        for (Future<DoubleCheckedLockingSingleton> future : futures) {
            DoubleCheckedLockingSingleton instance = future.get();
            instances.add(instance);
            dataValues.add(instance.getData());
        }

        executor.shutdown();

        // 인스턴스가 하나만 생성되었는지 확인
        assertEquals(1, instances.size(), "단일 인스턴스 생성됨을 검증");

        // 모든 스레드가 동일한 초기화 상태를 보고 있는지 확인
        assertEquals(1, dataValues.size(), "모든 인스턴스에 대한 data 필드가 42가 맞는지 확인");
    }

    @RepeatedTest(10)
    public void 성능_테스트() throws InterruptedException {
        int threadCount = 100;
        long start = System.nanoTime();
        testSingletonPerformance(DoubleCheckedLockingSingleton::getInstance, threadCount);
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