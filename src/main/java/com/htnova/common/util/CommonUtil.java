package com.htnova.common.util;

import com.htnova.common.base.BaseEntity;
import com.htnova.common.base.BaseMapStruct;
import com.htnova.common.base.BaseTree;
import org.apache.commons.lang3.RandomStringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CommonUtil {

    /**
     * 调用 list 的 toDto 方法
     */
    @SuppressWarnings("unchecked")
    public static <V, T> List<V> toDto(List<T> source, Class<? extends BaseMapStruct> mapStruct) {
        BaseMapStruct mapper = Mappers.getMapper(mapStruct);
        return mapper.toDto(source);
    }

    /**
     * 调用 list 的 toDto 方法
     */
    @SuppressWarnings("unchecked")
    public static <V, T> List<V> toEntity(List<T> source, Class<? extends BaseMapStruct> mapStruct) {
        BaseMapStruct mapper = Mappers.getMapper(mapStruct);
        return mapper.toEntity(source);
    }

    /**
     * list 转 tree
     */
    public static <T extends BaseTree<T>> List<T> listToTree(List<T> list) {
        if(CollectionUtils.isEmpty(list)) return null;
        Map<Long, T> idMap = list.stream().collect(Collectors.toMap(BaseEntity::getId, item -> item));
        return list.stream()
                .filter(item -> Objects.isNull(
                        idMap.computeIfPresent(item.getPid(), (a, b) -> {
                            List<T> newList = Optional.ofNullable(b.getChildren()).orElseGet(ArrayList::new);
                            newList.add(item);
                            b.setChildren(newList);
                            return b;
                        })
                )).collect(Collectors.toList());
    }

    /**
     * 获取随机字符串
     */
    public static String getRandomString(int length) {
        return RandomStringUtils.randomGraph(length);
    }

    /**
     * 获取随机数字
     */
    public static String getRandomNum(int length) {
        return RandomStringUtils.random(length, "0123456789");
    }

}
