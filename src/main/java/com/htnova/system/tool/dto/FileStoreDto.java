package com.htnova.system.tool.dto;

import com.htnova.common.base.BaseEntity;
import com.htnova.system.tool.entity.FileStore;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class FileStoreDto extends BaseEntity {
    /** 原始文件名 */
    @NotNull(message = "原始文件名不能为空")
    private String name;

    /** 磁盘真实文件名 */
    @NotNull(message = "磁盘真实文件名不能为空")
    private String realName;

    /** 文件大小 */
    @NotNull(message = "文件大小不能为空")
    private Double size;

    /** 文件类型 */
    private String type;

    /** url */
    @NotNull(message = "url不能为空")
    private String url;

    /** md5 值 */
    @NotNull(message = "md5 值不能为空")
    private String md5;

    /** 存储类型（本地，oss） */
    @NotNull(message = "存储类型（本地，oss）不能为空")
    private FileStore.StoreType storeType;

    // 前端查询使用
    private String ids;
}
