package com.yk.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器式单例
 */
public class ContainerSingleton {
    private final static Map<String, Object> ioc = new ConcurrentHashMap<>();
    private ContainerSingleton(){}
    public static Object getBean(String className) {
        synchronized (ioc) {
            if (!ioc.containsKey(className)){
                Object obj = null;
                try {
                    obj = Class.forName(className).newInstance();
                    ioc.put(className, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return obj;
            } else {
                return ioc.get(className);
            }
        }
    }


}
