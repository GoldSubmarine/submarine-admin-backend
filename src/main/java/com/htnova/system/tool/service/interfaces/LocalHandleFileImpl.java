package com.htnova.system.tool.service.interfaces;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.system.tool.entity.FileStore;
import java.io.*;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@ConditionalOnProperty(name = "upload.type", havingValue = "local", matchIfMissing = true)
public class LocalHandleFileImpl implements HandleFile {

    @Value("${upload.local.path}")
    private String uploadPath;

    @Resource private ServerProperties serverProperties;

    private String getPrefixUrl() {
        return serverProperties.getServlet().getContextPath() + "/file/download/";
    }

    @Override
    @Transactional
    public FileStore upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String fileHashPath = fileName.substring(0, 6).replaceAll("(\\w\\w)", "$1/");
        File fileDir = new File(uploadPath + fileHashPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        InputStream inputStream = file.getInputStream();
        try (FileOutputStream fileOutputStream =
                new FileOutputStream(
                        new File(fileDir.getAbsolutePath() + File.separator + fileName))) {
            byte[] buffer = new byte[1024];
            while (true) {
                int len = inputStream.read(buffer);
                if (len == -1) {
                    break;
                }
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error("文件上传失败：", e);
            throw new ServiceException(ResultStatus.UPLOAD_FAILED);
        }
        double size = file.getSize();
        return FileStore.builder()
                .size(size / 1000)
                .url(getPrefixUrl() + fileHashPath + fileName)
                .realName(fileName)
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .md5(DigestUtils.md5DigestAsHex(inputStream))
                .storeType(FileStore.StoreType.local)
                .build();
    }

    @Override
    @Transactional
    public void download(FileStore fileStore, HttpServletResponse response) throws IOException {
        File file = new File(uploadPath + StringUtils.remove(fileStore.getUrl(), getPrefixUrl()));
        if (!file.exists()) {
            throw new ServiceException(ResultStatus.FILE_NOT_FOUND);
        }
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = response.getOutputStream();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            IOUtils.copyLarge(fileInputStream, outputStream);
        } catch (Exception e) {
            log.error("读取文件失败", e);
        }
    }

    @Override
    @Transactional
    public void delete(FileStore fileStore) {
        String filePath = StringUtils.remove(fileStore.getUrl(), getPrefixUrl());
        File file = new File(uploadPath + filePath);
        if (file.delete()) {
            log.info("文件 {} 已被删除", file.getName());
        } else {
            log.info("文件 {} 删除失败", file.getName());
        }
    }
}
