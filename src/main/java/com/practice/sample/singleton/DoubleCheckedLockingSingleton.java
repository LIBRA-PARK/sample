package com.practice.sample.singleton;

public class DoubleCheckedLockingSingleton {
    private static DoubleCheckedLockingSingleton instance;

    private int data;

    private DoubleCheckedLockingSingleton() {
        // 인스턴스 생성 시 일부 작업 수행 (예: 데이터 초기화)
        // 인위적으로 지연을 주어 초기화 중에 다른 스레드가 접근할 가능성을 높임
        try {
            Thread.sleep(50); // 50ms 지연
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        data = 42; // 데이터 초기화
    }

    public static DoubleCheckedLockingSingleton getInstance() {
        if(instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if(instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }

    public int getData() {
        return data;
    }
}
