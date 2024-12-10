package com.practice.sample.singleton;

public class EagerSingleton {
    // 클래스가 로딩 될 때 객체 인스턴스화를 통해서 초기화
    private static final EagerSingleton instance = new EagerSingleton();
    // 프로그램 내에서 무분별한 인스턴스화를 막기위해서 생성자 private 화
    private EagerSingleton() { }
    // 생성된 객체를 재사용하기 위해서 의도적으로 이미 생성된 인스턴스를 반환해서 사용하도록 함
    public static EagerSingleton getInstance() {
        return instance;
    }
}
