package com.yk.structure_chapter.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 普通的动态代理
 */
public class JdkMeipo implements InvocationHandler {
    private IPerson target;
    public IPerson getInstance(IPerson target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return (IPerson) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(this.target, args);
        after();
        return result;
    }
    private void before() {

    }

    private void after() {

    }
}

class Customer implements IPerson {
    @Override
    public void findLove() {
        System.out.println("符合客户的要求");
    }
}
interface IPerson {
    void findLove();
}
