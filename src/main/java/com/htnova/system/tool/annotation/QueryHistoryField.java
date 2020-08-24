package com.htnova.system.tool.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记忆查询
 * 查询历史输入记录，标记在字段上即可
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface QueryHistoryField {
    /**
     * 默认将 fieldName 驼峰转下划线作为表字段
     * 也可以自行指定表字段
     */
    String value() default "";
}
