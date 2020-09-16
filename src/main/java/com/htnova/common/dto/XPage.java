package com.htnova.common.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.htnova.common.base.BaseMapStruct;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

public interface XPage<T> {
    long getPageSize();

    void setPageSize(long size);

    long getPageNum();

    void setPageNum(long num);

    long getTotal();

    void setTotal(long total);

    String getSort();

    void setSort(String order);

    Order getOrder();

    void setOrder(Order order);

    List<T> getData();

    void setData(List<T> orderItemList);

    static <V, T> IPage<V> toIPage(XPage<T> xPage) {
        Page<V> page = new Page<>();
        page.setSize(xPage.getPageSize());
        page.setCurrent(xPage.getPageNum());
        if (xPage.getOrder().equals(Order.ascending)) {
            page.setOrders(Lists.newArrayList(OrderItem.asc(xPage.getSort())));
        }
        if (xPage.getOrder().equals(Order.descending)) {
            page.setOrders(Lists.newArrayList(OrderItem.desc(xPage.getSort())));
        }
        return page;
    }

    static <T, V> XPage<T> fromIPage(IPage<V> iPage, BaseMapStruct<T, V> mapStructInstance) {
        XPageImpl<T> xPage = new XPageImpl<>();
        xPage.setPageNum(iPage.getCurrent());
        xPage.setPageSize(iPage.getSize());
        xPage.setTotal(iPage.getTotal());
        if (!CollectionUtils.isEmpty(iPage.orders())) {
            OrderItem orderItem = iPage.orders().get(0);
            xPage.setSort(orderItem.getColumn());
            if (orderItem.isAsc()) {
                xPage.setOrder(Order.ascending);
            } else {
                xPage.setOrder(Order.descending);
            }
        }

        xPage.setData(mapStructInstance.toDto(iPage.getRecords()));
        return xPage;
    }

    static <T> XPage<T> fromIPage(IPage<T> iPage) {
        XPageImpl<T> xPage = new XPageImpl<>();
        xPage.setPageNum(iPage.getCurrent());
        xPage.setPageSize(iPage.getSize());
        xPage.setTotal(iPage.getTotal());
        if (!CollectionUtils.isEmpty(iPage.orders())) {
            OrderItem orderItem = iPage.orders().get(0);
            xPage.setSort(orderItem.getColumn());
            if (orderItem.isAsc()) {
                xPage.setOrder(Order.ascending);
            } else {
                xPage.setOrder(Order.descending);
            }
        }
        xPage.setData(iPage.getRecords());
        return xPage;
    }

    @JsonIgnore
    default long getStartIndex() {
        return (this.getPageNum() - 1) * this.getPageSize();
    }

    @JsonIgnore
    default long getEndIndex() {
        return this.getPageNum() * this.getPageSize();
    }

    enum Order {
        descending,
        ascending,
    }
}
