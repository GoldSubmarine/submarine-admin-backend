package com.htnova.system.tool.service.interfaces;

import com.htnova.system.tool.entity.FileStore;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现类只操作文件，不操作数据库
 */
public interface HandleFile {

    FileStore upload(MultipartFile file) throws IOException;

    void download(FileStore fileStore, HttpServletResponse response) throws IOException;

    void delete(FileStore fileStore);
}
