package org.javahub.submarine.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 新增的时候自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createBy", "todo", metaObject);
        this.setFieldValByName("updateBy", "todo", metaObject);
    }

    // 更新的时候自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateBy", "todo", metaObject);
    }
}
