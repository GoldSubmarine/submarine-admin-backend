package com.htnova.common.base;

import java.util.List;

public interface BaseTree<T> {
    Long getId();

    void setId(Long id);

    Long getPid();

    void setPid(Long pid);

    String getPids();

    void setPids(String pids);

    List<T> getChildren();

    void setChildren(List<T> children);
}
