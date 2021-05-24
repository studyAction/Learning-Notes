package com.yk.structure_chapter.proxy;

public class Client {
    public static void main(String[] args) {
        JdkMeipo proxy = new JdkMeipo();
        IPerson zhao = proxy.getInstance(new Customer());
        zhao.findLove();


    }
}
