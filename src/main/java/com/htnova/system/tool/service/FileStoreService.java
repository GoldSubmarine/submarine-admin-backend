package com.htnova.system.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.tool.dto.FileStoreDto;
import com.htnova.system.tool.entity.FileStore;
import com.htnova.system.tool.mapper.FileStoreMapper;
import com.htnova.system.tool.service.interfaces.FileStoreInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class FileStoreService extends ServiceImpl<FileStoreMapper, FileStore> {

    @Resource
    private FileStoreMapper fileStoreMapper;

    @Resource
    private FileStoreInterface fileStoreInterface;

    @Transactional(readOnly = true)
    public IPage<FileStore> findFileStoreList(FileStoreDto fileStoreDto, IPage<Void> xPage) {
        return fileStoreMapper.findPage(xPage, fileStoreDto);
    }

    @Transactional(readOnly = true)
    public List<FileStore> findFileStoreList(FileStoreDto fileStoreDto) {
        return fileStoreMapper.findList(fileStoreDto);
    }

    @Transactional
    public void saveFileStore(FileStore fileStore) {
        super.saveOrUpdate(fileStore);
    }

    @Transactional
    public FileStore getFileStoreById(long id) {
        return fileStoreMapper.selectById(id);
    }

    @Transactional
    public void deleteFileStore(Long id) {
        fileStoreInterface.delete(this.getById(id));
        super.removeById(id);
    }

    @Transactional
    public FileStore upload(MultipartFile file) throws IOException {
        FileStore fileStore = fileStoreInterface.upload(file);
        saveFileStore(fileStore);
        return fileStore;
    }

    @Transactional
    public void download(String realName, HttpServletResponse response) throws IOException {
        FileStore fileStore = this.lambdaQuery().eq(FileStore::getRealName, realName).one();
        fileStoreInterface.download(fileStore, response);
    }

}
