package com.yk.create_chapter.prototype;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConcretePrototype implements Cloneable, Serializable {
    private String desc;
    private String name;
    private List<String> hobbies;

    public ConcretePrototype(String desc) {
        this.desc = desc;
    }

    /**
     * super.clone（）方法直接从堆内存中以二进制流的方式进行复制，重新分配一个内存块
     * 因此其效率很高。由于super.clone（）方法基于内存复制，因此不会调用对象的构造函数，也就是不需要经历初始化过程。
     * @return
     */
    @Override
    protected ConcretePrototype clone() {
        ConcretePrototype concretePrototype = null;
        try {
            concretePrototype = (ConcretePrototype) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return concretePrototype;
    }

    /**
     * 基于序列化的深克隆
     * @return  克隆完整内容的对象
     */
    public ConcretePrototype deepClone() {
        try {
            // oos基于bos构造，将this对象以字节数组的形式写入oos
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            // bis基于已经写入对象的bos的字节数组，作为ois读取对象的的数据源
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (ConcretePrototype) ois.readObject();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 仿照ArrayList的克隆
     * @return
     */
    public ConcretePrototype deepCloneHobbies() {
        try {
            ConcretePrototype result = (ConcretePrototype) super.clone();
            result.hobbies = (List)((ArrayList)result.hobbies).clone();
            return result;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public String toString() {
        return "ConcretePrototype{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
