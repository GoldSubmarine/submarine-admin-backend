package com.htnova.system.tool.service.interfaces;

import com.htnova.system.tool.entity.FileStore;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileStoreInterface {

    FileStore upload(MultipartFile file) throws IOException;

    void download(FileStore fileStore, HttpServletResponse response) throws IOException;

    void delete(FileStore fileStore);
}
