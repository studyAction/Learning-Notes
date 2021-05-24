package com.yk.create_chapter.buildtype;

/**
 * 通过静态内部类的链式调用，用builder来返回最后的结果
 */
public class Client {
    public static void main(String[] args) {
        Course course = new Course.Builder()
                .addName("a")
                .addVideo("b")
                .addNote("c")
                .addPpt("ppt")
                .builder();
        System.out.println(course);
    }
}
