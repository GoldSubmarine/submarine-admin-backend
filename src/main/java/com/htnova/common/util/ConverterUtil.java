package com.htnova.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.base.BaseMapStruct;
import com.htnova.common.base.BaseTree;
import com.htnova.common.dto.XPage;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConverterUtil {

    private ConverterUtil() {}

    public static <D, E extends BaseEntity> E toEntity(D d, Class<? extends BaseMapStruct<D, E>> c) {
        return Mappers.getMapper(c).toEntity(d);
    }

    public static <D, E extends BaseEntity> D toDto(E e, Class<? extends BaseMapStruct<D, E>> c) {
        return Mappers.getMapper(c).toDto(e);
    }

    public static <D, E extends BaseEntity> XPage<D> toDto(IPage<E> iPage, Class<? extends BaseMapStruct<D, E>> mapStruct){
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

    /**
     * 调用 list 的 toDto 方法
     */
    public static <D, E extends BaseEntity> List<D> toDto(List<E> source, Class<? extends BaseMapStruct<D,E>> mapStruct) {
        return Mappers.getMapper(mapStruct).toDto(source);
    }

    /**
     * 调用 list 的 toDto 方法
     */
    public static <D, E extends BaseEntity> List<E> toEntity(List<D> source, Class<? extends BaseMapStruct<D,E>> mapStruct) {
        return Mappers.getMapper(mapStruct).toEntity(source);
    }

    /**
     * list 转 树
     */
    public static <T extends BaseTree<T>> List<T> listToTree(List<T> list) {
        if(CollectionUtils.isEmpty(list)) return Lists.newArrayList();
        Map<Long, T> idMap = list.stream().collect(Collectors.toMap(BaseTree::getId, item -> item));
        return list.stream().filter(item -> {
            T treeEntity = idMap.get(item.getPid());
            if(Objects.nonNull(treeEntity)){
                if(Objects.isNull(treeEntity.getChildren())){
                    treeEntity.setChildren(Lists.newArrayList());
                }
                treeEntity.getChildren().add(item);
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }
}
