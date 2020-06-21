package com.htnova.system.workflow.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public abstract class ActBaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public abstract T getDetailByProcessInstanceId(String processInstanceId);
}
