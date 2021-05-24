package com.yk.create_chapter.singleton;

/**
 * 这种形式兼顾了饿汉式单例写法的内存浪费问题和synchronized的性能问题
 */
public class PerfectSingleton {
    /**
     * 解决了反射场景中的多次创建问题
     */
    private PerfectSingleton(){
        if (LazeHolder.INSTANCE != null) {
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    // static使单例模式的空间共享
    private static PerfectSingleton getInstance(){
        return LazeHolder.INSTANCE;
    }

    // Java本身语法，不加载内部类
    private static class LazeHolder {
        private static final PerfectSingleton INSTANCE = new PerfectSingleton();
    }
}
