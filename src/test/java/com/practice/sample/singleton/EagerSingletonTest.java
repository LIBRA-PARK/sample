package com.practice.sample.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class EagerSingletonTest {
    @Test
    void 단일_인스턴스_테스트() {
        EagerSingleton eagerSingleton1 = EagerSingleton.getInstance();
        EagerSingleton eagerSingleton2 = EagerSingleton.getInstance();

        assertSame(eagerSingleton1, eagerSingleton2, "단일 인스턴스가 아닙니다.");
    }

    @Test
    void 인스턴스_Not_null() {
        EagerSingleton eagerSingleton = EagerSingleton.getInstance();

        assertNotNull(eagerSingleton, "인스턴스는 null이 아니어야 합니다.");
    }
}