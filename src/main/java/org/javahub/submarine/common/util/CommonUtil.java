package org.javahub.submarine.common.util;

import org.apache.commons.lang3.StringUtils;
import org.javahub.submarine.base.BaseMapStruct;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonUtil {

    /**
     * 调用 list 的 toDto 方法
     */
    public static <V, T> List<V> toDto(List<T> source, Class<? extends BaseMapStruct> mapStruct) {
        BaseMapStruct mapper = Mappers.getMapper(mapStruct);
        return mapper.toDto(source);
    }

    /**
     * 调用 list 的 toDto 方法
     */
    public static <V, T> List<V> toEntity(List<T> source, Class<? extends BaseMapStruct> mapStruct) {
        BaseMapStruct mapper = Mappers.getMapper(mapStruct);
        return mapper.toEntity(source);
    }

    /**
     * list 转 dto
     */
    private static <V, T> List<V> invokeMethod(List<T> source, String methodName) {
        if(source == null) return null;
        List<V> targetList = new ArrayList<>();
        if(source.isEmpty()) return targetList;
        try {
            return source.stream().map(item -> (V) invokeMethod(item, methodName)).collect(Collectors.toList());
        } catch (Exception e) {
            return targetList;
        }
    }

    /**
     * 反射调用 item 的 toDto 方法
     */
    public static <V, T> V itemToDto(T item) {
        return invokeMethod(item, "toDto");
    }

    /**
     * 反射调用 item 的 toDto 方法
     */
    public static <V, T> V itemToEntity(T item) {
        return invokeMethod(item, "toEntity");
    }

    /**
     * 反射调用 item 的方法，获取返回值
     */
    private static <V, T> V invokeMethod(T item, String methodName) {
        try {
            Class<?> sourceClass = item.getClass();
            Method toDto = sourceClass.getMethod(methodName);
            toDto.setAccessible(true);
            return (V) toDto.invoke(item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
            List<Object> idList = new ArrayList<>();
            List<Object> pidList = new ArrayList<>();
            // 循环将自己放入父级的list中
            for (T item : list) {
                Object itemId = getId.invoke(item);
                idList.add(itemId);
                Object itemPid = getPid.invoke(item);
                pidList.add(itemPid);
                List<T> itemParentList = listMap.get(itemPid);
                if(itemParentList == null) {
                    List<T> itemList = new ArrayList<>();
                    itemList.add(item);
                    listMap.put(itemPid, itemList);
                } else {
                    itemParentList.add(item);
                }
            }
            // 获取到root节点的pid
            pidList.removeAll(idList);
            for (T item : list) {
                Object itemId = getId.invoke(item);
                Object itemPid = getPid.invoke(item);
                if(pidList.contains(itemPid)) resultList.add(item);
                List<T> itemChild = listMap.get(itemId);
                setChildren.invoke(item, itemChild);
            }
            return resultList;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return resultList;
    }

}
