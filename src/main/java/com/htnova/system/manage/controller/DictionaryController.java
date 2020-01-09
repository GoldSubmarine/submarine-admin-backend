package com.htnova.system.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.manage.dto.DictionaryDto;
import com.htnova.system.manage.entity.Dictionary;
import com.htnova.system.manage.mapstruct.DictionaryMapStruct;
import com.htnova.system.manage.service.DictionaryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 字典
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    /**
     * 分页查询
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/list/page")
    public XPage<DictionaryDto> findListByPage(DictionaryDto dictionaryDto, XPage<Void> xPage) {
        IPage<Dictionary> dictionaryPage = dictionaryService.findDictionaryList(dictionaryDto, xPage);
        return DtoConverter.toDto(dictionaryPage, DictionaryMapStruct.class);
    }

    /**
     * 查询
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/list/all")
    public List<DictionaryDto> findList(DictionaryDto dictionaryDto) {
        List<Dictionary> dictionaryList = dictionaryService.findDictionaryList(dictionaryDto);
        return DtoConverter.toDto(dictionaryList, DictionaryMapStruct.class);
    }

    /**
     * 详情
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/detail")
    public DictionaryDto getById(long id) {
        Dictionary dictionary = dictionaryService.getDictionaryById(id);
        return DtoConverter.toDto(dictionary, DictionaryMapStruct.class);
    }

    /**
     * 保存
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.add', 'dictionary.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody DictionaryDto dictionaryDto) {
        dictionaryService.saveDictionary(DtoConverter.toEntity(dictionaryDto, DictionaryMapStruct.class));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.del')")
    @DeleteMapping("/del")
    public Result<Void> delete(@RequestBody DictionaryDto dictionaryDto) {
        dictionaryService.deleteDictionary(dictionaryDto.getId());
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
