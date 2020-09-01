package com.htnova.common.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XPageImpl<T> implements XPage<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    /** 总数 */
    private long total = 0;

    /** 每页显示条数，默认 10 */
    private long pageSize = 10;

    /** 当前页 */
    private long pageNum = 1;

    /** 排序字段信息 */
    @Builder.Default
    private String sort = "create_time";

    /** 排序字段信息 */
    @Builder.Default
    private Order order = Order.descending;

    /** 查询数据列表 */
    private List<T> data = new ArrayList<>();
}
