package com.htnova.system.tool.service;

import com.htnova.system.tool.entity.FileStore;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/** 实现类只操作文件，不操作数据库 */
public interface HandleFile {
    FileStore upload(MultipartFile file) throws IOException;

    void download(FileStore fileStore, HttpServletResponse response) throws IOException;

    void delete(FileStore fileStore);
}
