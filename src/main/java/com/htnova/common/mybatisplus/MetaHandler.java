package com.htnova.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.constant.GlobalConst;
import com.htnova.common.util.UserUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MetaHandler implements MetaObjectHandler {

    /** 新增的时候自动填充 */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(GlobalConst.ZONE_ID), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(GlobalConst.ZONE_ID), metaObject);
        this.setFieldValByName("createBy", UserUtil.getAuthUser().getId(), metaObject);
        this.setFieldValByName("updateBy", UserUtil.getAuthUser().getId(), metaObject);
    }

    /** 更新的时候自动填充 */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(GlobalConst.ZONE_ID), metaObject);
        this.setFieldValByName("updateBy", UserUtil.getAuthUser().getId(), metaObject);
    }
}
