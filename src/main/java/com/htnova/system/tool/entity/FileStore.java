package com.htnova.system.tool.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_file_store")
public class FileStore extends BaseEntity {
    /** 原始文件名 */
    private String name;

    /** 磁盘真实文件名 */
    private String realName;

    /** 文件大小 */
    private Double size;

    /** 文件类型 */
    private String type;

    /** url */
    private String url;

    /** md5 值 */
    private String md5;

    /** 存储类型（本地，oss） */
    private StoreType storeType;

    public enum StoreType {
        local,
        OSS,
    }
}
