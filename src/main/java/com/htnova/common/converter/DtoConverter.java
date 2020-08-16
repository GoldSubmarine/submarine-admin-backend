package com.htnova.common.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.base.BaseMapStruct;
import com.htnova.common.dto.XPage;
import java.util.List;
import org.mapstruct.factory.Mappers;

public class DtoConverter {

    private DtoConverter() {}

    public static <D, E> E toEntity(D d, Class<? extends BaseMapStruct<D, E>> c) {
        return Mappers.getMapper(c).toEntity(d);
    }

    public static <D, E> D toDto(E e, Class<? extends BaseMapStruct<D, E>> c) {
        return Mappers.getMapper(c).toDto(e);
    }

    public static <D, E> XPage<D> toDto(IPage<E> iPage, Class<? extends BaseMapStruct<D, E>> mapStruct) {
        return XPage.fromIPage(iPage, mapStruct);
    }

    /** 调用 list 的 toDto 方法 */
    public static <D, E> List<D> toDto(List<E> source, Class<? extends BaseMapStruct<D, E>> mapStruct) {
        return Mappers.getMapper(mapStruct).toDto(source);
    }

    /** 调用 list 的 toDto 方法 */
    public static <D, E> List<E> toEntity(List<D> source, Class<? extends BaseMapStruct<D, E>> mapStruct) {
        return Mappers.getMapper(mapStruct).toEntity(source);
    }
}
