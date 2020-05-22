package com.htnova.system.tool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.tool.dto.FileStoreDto;
import com.htnova.system.tool.entity.FileStore;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileStoreMapper extends BaseMapper<FileStore> {
    IPage<FileStore> findPage(IPage<Void> xPage, @Param("fileStoreDto") FileStoreDto fileStoreDto);

    List<FileStore> findList(@Param("fileStoreDto") FileStoreDto fileStoreDto);
}
