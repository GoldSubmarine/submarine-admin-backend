package com.htnova.system.tool.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.htnova.system.tool.entity.FileStore;
import com.htnova.system.tool.service.HandleFile;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@ConditionalOnProperty(value = "upload.type", havingValue = "oss")
@Service
public class OSSHandleFileImpl implements HandleFile {
    @Value("${upload.oss.namespace}")
    private String namespace;

    @Value("${upload.oss.endpoint}")
    private String endpoint;

    @Value("${upload.oss.viewEndpoint}")
    private String viewEndpoint;

    @Value("${upload.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${upload.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${upload.oss.bucketName}")
    private String bucketName;

    private String getPrefix() {
        return "https://" + bucketName + "." + viewEndpoint + "/";
    }

    @Override
    public FileStore upload(MultipartFile file) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String fileName = UUID.randomUUID().toString();
        String fileHashPath = fileName.substring(0, 6).replaceAll("(\\w\\w)", "$1/");
        String url = namespace + "/" + fileHashPath + fileName;
        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, url, file.getInputStream());
            log.info("file upload success. requestId: {}, ", putObjectResult.getRequestId());
            return FileStore
                .builder()
                .size((double) file.getSize() / 1000)
                .url(getPrefix() + url)
                .realName(fileName)
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .md5(DigestUtils.md5DigestAsHex(file.getInputStream()))
                .storeType(FileStore.StoreType.OSS)
                .build();
        } catch (Exception e) {
            log.error("file upload error", e);
            throw e;
        } finally {
            try {
                ossClient.shutdown();
            } catch (Exception e) {
                log.info("cos client shutdown error", e);
            }
        }
    }

    @Override
    public void download(FileStore fileStore, HttpServletResponse response) {
        // 直接走oss，不从服务器下载
    }

    @Override
    public void delete(FileStore fileStore) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, StringUtils.remove(fileStore.getUrl(), getPrefix()));
    }
}
