package com.practice.sample.singleton;

public class HolderSingleton {
    private HolderSingleton() { }

    private static class Holder {
        private static final HolderSingleton instance = new HolderSingleton();
    }

    public static HolderSingleton getInstance() {
        return Holder.instance;
    }
}
