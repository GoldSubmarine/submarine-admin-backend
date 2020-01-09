package com.htnova.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.htnova.common.util.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MetaHandler implements MetaObjectHandler {
    //todo
    // 新增的时候自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        String username = UserUtil.getAuthUser().getUsername();
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("createBy", username, metaObject);
        this.setFieldValByName("updateBy", username, metaObject);
    }

    // 更新的时候自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        String username = UserUtil.getAuthUser().getUsername();
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", username, metaObject);
    }
}
