package com.smart.algorithm.singleton;

/**
 *
 * 懒汉模式 线程不安全
 * Created by  fc.w on 2017/1/13.
 */
public class SingletonLazy {

    private static SingletonLazy singleton;

    private SingletonLazy() {
    }

    public static SingletonLazy getInstance() {
        if (singleton == null) {
            singleton = new SingletonLazy();
        }

        return singleton;
    }
}
