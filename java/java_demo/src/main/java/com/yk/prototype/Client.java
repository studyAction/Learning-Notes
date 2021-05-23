package com.yk.prototype;

public class Client {
    public static void main(String[] args) {
        ConcretePrototype concretePrototype = new ConcretePrototype("origin");
        System.out.println(concretePrototype);

        ConcretePrototype newType = concretePrototype.clone();
        newType.setDesc("clone");
        System.out.println(newType);
    }
}
