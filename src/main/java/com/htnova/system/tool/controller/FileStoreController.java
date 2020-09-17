package com.htnova.system.tool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.tool.dto.FileStoreDto;
import com.htnova.system.tool.entity.FileStore;
import com.htnova.system.tool.mapstruct.FileStoreMapStruct;
import com.htnova.system.tool.service.FileStoreService;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** @menu 文件存储 */
@RestController
@RequestMapping("/file")
public class FileStoreController {
    @Resource
    private FileStoreService fileStoreService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/list/page")
    public XPage<FileStoreDto> findListByPage(FileStoreDto fileStoreDto, XPage<Void> xPage) {
        IPage<FileStore> fileStorePage = fileStoreService.findFileStoreList(fileStoreDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(fileStorePage, FileStoreMapStruct.INSTANCE);
    }

    /** 查询 */
    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/list/all")
    public List<FileStoreDto> findList(FileStoreDto fileStoreDto) {
        List<FileStore> fileStoreList = fileStoreService.findFileStoreList(fileStoreDto);
        return DtoConverter.toDto(fileStoreList, FileStoreMapStruct.INSTANCE);
    }

    /** 根据ids查询 */
    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/list/ids")
    public List<FileStoreDto> findListByIds(FileStoreDto fileStoreDto) {
        List<FileStore> fileStoreList = fileStoreService.findFileStoreByIds(fileStoreDto.getIds());
        return DtoConverter.toDto(fileStoreList, FileStoreMapStruct.INSTANCE);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/detail/{id}")
    public FileStoreDto getById(@PathVariable long id) {
        FileStore fileStore = fileStoreService.getFileStoreById(id);
        return DtoConverter.toDto(fileStore, FileStoreMapStruct.INSTANCE);
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('fileStore.add', 'fileStore.edit')")
    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody FileStoreDto fileStoreDto) {
        fileStoreService.saveFileStore(DtoConverter.toEntity(fileStoreDto, FileStoreMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('fileStore.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        fileStoreService.deleteFileStore(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }

    /** 上传附件 */
    @PostMapping("/upload")
    public Result<FileStoreDto> upload(@RequestParam MultipartFile file) throws IOException {
        FileStore fileStore = fileStoreService.upload(file);
        return Result.build(ResultStatus.SAVE_SUCCESS, DtoConverter.toDto(fileStore, FileStoreMapStruct.INSTANCE));
    }

    /** 下载附件 */
    @GetMapping("/download/**/{realName}")
    public void download(@PathVariable String realName, HttpServletResponse httpServletResponse) throws IOException {
        fileStoreService.download(realName, httpServletResponse);
    }
}
