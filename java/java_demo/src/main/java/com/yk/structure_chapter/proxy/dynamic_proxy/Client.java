package com.yk.structure_chapter.proxy.dynamic_proxy;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    public static void main(String[] args) {
        try {
            Order order = new Order();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse("2021/05/21");
            order.setCreateTime(date.getTime());

            // 注意：这里的强转类型必须为接口类型，强转表示为IService代理
            IOrderService iOrderService = (IOrderService) new OrderServiceDynamicProxy().
                    getInstance(new OrderService(new OrderDao()));
            iOrderService.createOrder(order);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
