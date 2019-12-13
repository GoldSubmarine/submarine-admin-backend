package com.htnova.common.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.base.BaseMapStruct;
import com.htnova.common.dto.XPage;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class DtoConverter {

    private DtoConverter() {}

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

}
