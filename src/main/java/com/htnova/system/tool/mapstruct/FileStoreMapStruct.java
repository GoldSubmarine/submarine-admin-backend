package com.htnova.system.tool.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.tool.dto.FileStoreDto;
import com.htnova.system.tool.entity.FileStore;
import org.mapstruct.Mapper;

@Mapper
public interface FileStoreMapStruct extends BaseMapStruct<FileStoreDto, FileStore> {}
