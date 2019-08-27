package org.javahub.submarine.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.javahub.submarine.common.constant.GlobalConst;
import org.javahub.submarine.common.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class XPage2<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    /**
     * 当前页码
     */
    private Integer pageNum = GlobalConst.PAGE_NUM;

    /**
     * 每页数量
     */
    private Integer pageSize = GlobalConst.PAGE_SIZE;

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 排序字段
     */
    private String orderBy = "create_time DESC";

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 包装pageInfo对象
     */
    @JsonIgnore
    private PageInfo pageInfo;

    //    初始化排序
    public XPage2() {
        super();
        pageInfo = new PageInfo();
    }

    public List<T> getList() {
        return pageInfo.getList();
    }

    public void setList(List<T> list) {
        pageInfo.setList(list);
    }

    public long getTotal() {
        return pageInfo.getTotal();
    }

    /**
     * page 转 dto
     */
    public <V> XPage<V> toDto() {
        XPage<V> target = new XPage<>();
        BeanUtils.copyProperties(this, target);

        if(CollectionUtils.isEmpty(this.getList())) {
            return target;
        }
        try {
            List<V> dtoList = this.getList().stream().map(item -> (V) CommonUtil.itemToDto(item)).collect(Collectors.toList());
            target.setList(dtoList);
        } catch (Exception e) {
            return target;
        }
        return target;
    }

    public void startPage() {
        PageHelper.startPage(pageNum, pageSize, orderBy);
    }

    public void loadList(List<T> list) {
        pageInfo = new PageInfo(list);
    }
}
