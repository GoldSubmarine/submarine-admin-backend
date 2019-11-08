package com.htnova.scaffold.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.common.exception.ServiceException;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
    public static <T> List<T> listToTree(List<T> list) {
        return listToTree(list, "id", "pid", "children");
    }

    /**
     * list 转 tree
     * @param id: 主键名
     * @param pid: 父级id名称
     * @param children: children的名称
     * @return List<T>: 返回值为list，可能有多个root节点
     */
    public static <T> List<T> listToTree(List<T> list, String id, String pid, String children) {
        List<T> resultList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)) return null;
        Class<?> sourceClass = list.get(0).getClass();
        try {
            Method getId = sourceClass.getMethod(String.format("get%s", StringUtils.capitalize(id)));
            Method getPid = sourceClass.getMethod(String.format("get%s", StringUtils.capitalize(pid)));
            Method setChildren = sourceClass.getMethod(String.format("set%s", StringUtils.capitalize(children)), List.class);
            Map<Object, List<T>> listMap = new HashMap<>();
            Set<Object> idSet = new HashSet<>();
            Set<Object> pidSet = new HashSet<>();
            // 循环将自己放入父级的list中
            for (T item : list) {
                Object itemId = getId.invoke(item);
                idSet.add(itemId);
                Object itemPid = getPid.invoke(item);
                pidSet.add(itemPid);
                List<T> itemParentList = listMap.get(itemPid);
                if(itemParentList == null) {
                    List<T> itemList = new ArrayList<>();
                    itemList.add(item);
                    listMap.put(itemPid, itemList);
                } else {
                    itemParentList.add(item);
                }
            }
            for (T item : list) {
                Object itemId = getId.invoke(item);
                Object itemPid = getPid.invoke(item);
                if(pidSet.contains(itemPid) && !idSet.contains(itemPid)) resultList.add(item);
                List<T> itemChild = listMap.get(itemId);
                setChildren.invoke(item, itemChild);
            }
            return resultList;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException();
        }
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
