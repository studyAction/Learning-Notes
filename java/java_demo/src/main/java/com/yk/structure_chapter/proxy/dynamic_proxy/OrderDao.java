package com.yk.structure_chapter.proxy.dynamic_proxy;

public class OrderDao {
    public int insert(Order order) {
        System.out.println("OrderDao 创建Order成功！");
        return 1;
    }
}
