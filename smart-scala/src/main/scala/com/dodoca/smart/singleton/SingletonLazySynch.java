package com.dodoca.smart.singleton;

/**
 * 懒汉 线程安全，效率低
 * Created by  fc.w on 2017/1/13.
 */
public class SingletonLazySynch {
    private static SingletonLazySynch singleton;

    private SingletonLazySynch() {}

    public static synchronized SingletonLazySynch getInstance() {
        if (singleton == null) {
            singleton = new SingletonLazySynch();
        }

        return singleton;
    }
}
