package com.htnova.common.converter;

import com.google.common.collect.Lists;
import com.htnova.common.base.BaseTree;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class TreeConverter {

    private TreeConverter() {}

    /** list 转 树 */
    public static <T extends BaseTree<T>> List<T> listToTree(List<T> list) {
        if (CollectionUtils.isEmpty(list)) return Lists.newArrayList();
        Map<Long, T> idMap = list.stream().collect(Collectors.toMap(BaseTree::getId, item -> item));
        return list
            .stream()
            .filter(
                item -> {
                    T treeEntity = idMap.get(item.getPid());
                    if (Objects.nonNull(treeEntity)) {
                        if (Objects.isNull(treeEntity.getChildren())) {
                            treeEntity.setChildren(Lists.newArrayList());
                        }
                        treeEntity.getChildren().add(item);
                        return false;
                    }
                    return true;
                }
            )
            .collect(Collectors.toList());
    }
}
