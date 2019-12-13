package com.htnova.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.dto.DictionaryItemDto;
import com.htnova.system.entity.DictionaryItem;
import com.htnova.system.mapstruct.DictionaryItemMapStruct;
import com.htnova.system.service.DictionaryItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 字典详情
 */
@RestController
@RequestMapping("/dictionary-item")
public class DictionaryItemController {

    @Resource
    private DictionaryItemService dictionaryItemService;

    /**
     * 分页查询
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/list/page")
    public XPage<DictionaryItemDto> findListByPage(DictionaryItemDto dictionaryItemDto, XPage<Void> xPage) {
        IPage<DictionaryItem> dictionaryItemPage = dictionaryItemService.findDictionaryItemList(dictionaryItemDto, xPage);
        return DtoConverter.toDto(dictionaryItemPage, DictionaryItemMapStruct.class);
    }

    /**
     * 查询
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/list/all")
    public List<DictionaryItemDto> findList(DictionaryItemDto dictionaryItemDto) {
        List<DictionaryItem> dictionaryItemList = dictionaryItemService.findDictionaryItemList(dictionaryItemDto);
        return DtoConverter.toDto(dictionaryItemList, DictionaryItemMapStruct.class);
    }

    /**
     * 详情
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/detail")
    public DictionaryItemDto getById(long id) {
        DictionaryItem dictionaryItem = dictionaryItemService.getDictionaryItemById(id);
        return DtoConverter.toDto(dictionaryItem, DictionaryItemMapStruct.class);
    }

    /**
     * 保存
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.add', 'dictionary.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody DictionaryItemDto dictionaryItemDto) {
        dictionaryItemService.saveDictionaryItem(DtoConverter.toEntity(dictionaryItemDto, DictionaryItemMapStruct.class));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.del')")
    @DeleteMapping("/del")
    public Result<Void> delete(@RequestBody DictionaryItemDto dictionaryItemDto) {
        dictionaryItemService.deleteDictionaryItem(dictionaryItemDto.getId());
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
