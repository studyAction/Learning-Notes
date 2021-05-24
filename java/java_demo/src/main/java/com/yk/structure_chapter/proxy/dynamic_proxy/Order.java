package com.yk.structure_chapter.proxy.dynamic_proxy;

import lombok.Data;

@Data
public class Order {
    private Object orderInfo;
    private Long createTime;
    private String id;
}
