package org.javahub.submarine.common.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.CaseFormat;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.javahub.submarine.common.util.CommonUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EqualsAndHashCode
@JsonIgnoreProperties({"records", "size", "current", "orders", "searchCount", "pages"})
public class XPage<T> extends Page<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总条数
     */
    private Integer total;

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

    public void setPageNum(Integer pageNum) {
        super.setCurrent(pageNum);
    }

    public long getPageSize() {
        return super.getSize();
    }

    public void setPageSize(Integer pageSize) {
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
    public <V> XPage<V> toDto() {
        XPage<V> target = new XPage<>();
        BeanUtils.copyProperties(this, target);
        if(this.getRecords().isEmpty()) {
            return target;
        }
        try {
            List<V> dtoList = this.getRecords().stream().map(item -> (V) CommonUtil.itemToDto(item)).collect(Collectors.toList());
            target.setList(dtoList);
        } catch (Exception e) {
            return target;
        }
        return target;
    }


    /**
     * 单个 item 转 dto
     */
//    private static Object itemToDto(Object item) {
//        try {
//            Class<?> sourceClass = item.getClass();
//            Method toDto = sourceClass.getMethod("toDto");
//            toDto.setAccessible(true);
//            return toDto.invoke(item);
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
