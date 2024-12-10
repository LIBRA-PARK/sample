package com.practice.sample.singleton;

public class SynchronizedLazySingleton {
    private static SynchronizedLazySingleton instance;

    private SynchronizedLazySingleton() { }

    public static synchronized SynchronizedLazySingleton getInstance() {
        if(instance == null) {
            instance = new SynchronizedLazySingleton();
        }
        return instance;
    }
}
