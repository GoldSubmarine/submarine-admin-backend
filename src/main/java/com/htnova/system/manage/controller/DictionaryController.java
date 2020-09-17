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
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 字典 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Resource
    private DictionaryService dictionaryService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('dictionary.find')")
    @GetMapping("/list/page")
    public XPage<DictionaryDto> findListByPage(DictionaryDto dictionaryDto, XPage<Void> xPage) {
        IPage<Dictionary> dictionaryPage = dictionaryService.findDictionaryList(dictionaryDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(dictionaryPage, DictionaryMapStruct.INSTANCE);
    }

    /** 查询 */
    @PreAuthorize("hasAnyAuthority('dictionary.find')")
    @GetMapping("/list/all")
    public List<DictionaryDto> findList(DictionaryDto dictionaryDto) {
        List<Dictionary> dictionaryList = dictionaryService.findDictionaryList(dictionaryDto);
        return DtoConverter.toDto(dictionaryList, DictionaryMapStruct.INSTANCE);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('dictionary.find')")
    @GetMapping("/detail/{id}")
    public DictionaryDto getById(@PathVariable long id) {
        Dictionary dictionary = dictionaryService.getDictionaryById(id);
        return DtoConverter.toDto(dictionary, DictionaryMapStruct.INSTANCE);
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('dictionary.add', 'dictionary.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody DictionaryDto dictionaryDto) {
        dictionaryService.saveDictionary(DtoConverter.toEntity(dictionaryDto, DictionaryMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('dictionary.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        dictionaryService.deleteDictionary(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
