package com.htnova.common.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.htnova.common.base.BaseMapStruct;
import lombok.Data;
import org.mapstruct.factory.Mappers;

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

    /**
     * page 转 dto
     */
    public static <D,E> XPage<D> toDto(IPage<E> iPage, Class<? extends BaseMapStruct<D,E>> mapStruct) {
        XPage<D> resultPage = new XPage<>();
        resultPage.setTotal(iPage.getTotal());
        resultPage.setSize(iPage.getSize());
        resultPage.setCurrent(iPage.getCurrent());
        resultPage.setOrders(iPage.orders());
        resultPage.setOptimizeCountSql(iPage.optimizeCountSql());
        resultPage.setSearchCount(iPage.isSearchCount());
        BaseMapStruct<D,E> mapper = Mappers.getMapper(mapStruct);
        resultPage.setRecords(mapper.toDto(iPage.getRecords()));
        return resultPage;
    }

}
