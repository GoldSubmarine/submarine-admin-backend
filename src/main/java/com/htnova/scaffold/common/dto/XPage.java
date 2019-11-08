package com.htnova.scaffold.common.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.CaseFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import com.htnova.scaffold.common.base.BaseMapStruct;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
@JsonIgnoreProperties({"records", "size", "current", "orders", "searchCount", "pages"})
@ToString(callSuper=false)
public class XPage<T> extends Page<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    /**
     * 当前页码
     */
    private Long pageNum;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 排序字段
     */
    private String sortable = "create_time DESC";

    /**
     * 数据列表
     */
    private List<T> list;


//    初始化排序
    public XPage() {
        super();
        setSortable(sortable);
    }

    public XPage(long pageNum, long pageSize) {
        super(pageNum, pageSize);
    }

    public long getPageNum() {
        return super.getCurrent();
    }

    public void setPageNum(long pageNum) {
        super.setCurrent(pageNum);
    }

    public long getPageSize() {
        return super.getSize();
    }

    public void setPageSize(long pageSize) {
        super.setSize(pageSize);
    }

    public List<T> getList() {
        return super.getRecords();
    }

    public void setList(List<T> list) {
        super.setRecords(list);
    }

    @Override
    public long getTotal() {
        return super.getTotal();
    }

    public String getSortable() {
        return sortable;
    }

    public void setSortable(String sortable) {
        OrderItem orderItem = null;
        this.sortable = sortable;
        String column = StringUtils.substringBefore(sortable, " ");
        column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);
        String order = StringUtils.substringAfter(sortable, " ");
        if(StringUtils.equalsIgnoreCase(order, "desc")) {
            orderItem = OrderItem.desc(column);
        } else if(StringUtils.equalsIgnoreCase(order, "asc")) {
            orderItem = OrderItem.asc(column);
        }
        if(Objects.nonNull(orderItem)) {
            super.setOrders(Arrays.asList(orderItem));
        } else {
            super.setOrders(new ArrayList<>());
        }
    }

    /**
     * page 转 dto
     */
    @SuppressWarnings("unchecked")
    public <V> XPage<V> toDto(Class<? extends BaseMapStruct> mapStruct) {
        XPage<V> resultPage = new XPage<>();
        resultPage.setPageNum(getPageNum());
        resultPage.setPageSize(getPageSize());
        resultPage.setTotal(getTotal());
        resultPage.setSortable(getSortable());
        BaseMapStruct mapper = Mappers.getMapper(mapStruct);
        resultPage.setList(mapper.toDto(getList()));
        return resultPage;
    }

}
