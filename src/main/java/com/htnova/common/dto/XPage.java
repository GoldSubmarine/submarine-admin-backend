package com.htnova.common.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.Data;

@Data
public class XPage<T> extends Page<T> {

    private static final long serialVersionUID = 5194933845448697148L;

    /**
     * 默认排序
     */
    private OrderItem defaultOrderItem = OrderItem.desc("create_time");

    public XPage() {
        super();
        //初始化排序
        this.setOrders(Lists.newArrayList(defaultOrderItem));
    }
}
