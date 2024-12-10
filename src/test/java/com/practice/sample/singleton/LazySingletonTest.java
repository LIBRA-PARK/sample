package com.practice.sample.singleton;

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

import static org.junit.jupiter.api.Assertions.*;

class LazySingletonTest {
    @Test
    void 멀티_스레드_환경_인스턴스_테스트() throws ExecutionException, InterruptedException {
        // 스레드 수 설정
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Callable을 사용하여 getInstance()를 호출
        Callable<LazySingleton> task = LazySingleton::getInstance;

        // Future 리스트에 모든 태스크 제출
        List<Future<LazySingleton>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(task));
        }

        // 모든 인스턴스를 수집
        Set<LazySingleton> instances = new HashSet<>();
        for (Future<LazySingleton> future : futures) {
            LazySingleton instance = future.get();
            instances.add(instance);
        }

        // 스레드 종료
        executor.shutdown();

        // 인스턴스의 크기를 검증
        if (instances.size() != 1) {
            System.out.println("생성된 인스턴스 갯수: " + instances.size());
            System.out.println("인스턴트들의 HashCodes:");
            for (LazySingleton instance : instances) {
                System.out.println(System.identityHashCode(instance));
            }
        }

        // 단일 인스턴스가 아니면 테스트 실패
        assertEquals(1, instances.size(), "Multiple instances were created, singleton is not enforced.");
    }
}