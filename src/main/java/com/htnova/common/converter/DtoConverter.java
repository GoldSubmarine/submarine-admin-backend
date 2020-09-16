package com.htnova.common.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.base.BaseMapStruct;
import com.htnova.common.dto.XPage;
import java.util.List;

public class DtoConverter {

    private DtoConverter() {}

    public static <D, E> E toEntity(D d, BaseMapStruct<D, E> instance) {
        return instance.toEntity(d);
    }

    public static <D, E> D toDto(E e, BaseMapStruct<D, E> instance) {
        return instance.toDto(e);
    }

    public static <D, E> XPage<D> toDto(IPage<E> iPage, BaseMapStruct<D, E> instance) {
        return XPage.fromIPage(iPage, instance);
    }

    /** 调用 list 的 toDto 方法 */
    public static <D, E> List<D> toDto(List<E> source, BaseMapStruct<D, E> instance) {
        return instance.toDto(source);
    }

    /** 调用 list 的 toDto 方法 */
    public static <D, E> List<E> toEntity(List<D> source, BaseMapStruct<D, E> instance) {
        return instance.toEntity(source);
    }
}
