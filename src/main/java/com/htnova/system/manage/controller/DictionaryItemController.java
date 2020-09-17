package com.htnova.system.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.manage.dto.DictionaryItemDto;
import com.htnova.system.manage.entity.DictionaryItem;
import com.htnova.system.manage.mapstruct.DictionaryItemMapStruct;
import com.htnova.system.manage.service.DictionaryItemService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 字典详情 */
@RestController
@RequestMapping("/dictionary-item")
public class DictionaryItemController {
    @Resource
    private DictionaryItemService dictionaryItemService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('dictionary.find')")
    @GetMapping("/list/page")
    public XPage<DictionaryItemDto> findListByPage(DictionaryItemDto dictionaryItemDto, XPage<Void> xPage) {
        IPage<DictionaryItem> dictionaryItemPage = dictionaryItemService.findDictionaryItemList(
            dictionaryItemDto,
            XPage.toIPage(xPage)
        );
        return DtoConverter.toDto(dictionaryItemPage, DictionaryItemMapStruct.INSTANCE);
    }

    /** 查询 */
    @PreAuthorize("hasAnyAuthority('dictionary.find')")
    @GetMapping("/list/all")
    public List<DictionaryItemDto> findList(DictionaryItemDto dictionaryItemDto) {
        List<DictionaryItem> dictionaryItemList = dictionaryItemService.findDictionaryItemList(dictionaryItemDto);
        return DtoConverter.toDto(dictionaryItemList, DictionaryItemMapStruct.INSTANCE);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('dictionary.find')")
    @GetMapping("/detail/{id}")
    public DictionaryItemDto getById(@PathVariable long id) {
        DictionaryItem dictionaryItem = dictionaryItemService.getDictionaryItemById(id);
        return DtoConverter.toDto(dictionaryItem, DictionaryItemMapStruct.INSTANCE);
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('dictionary.add', 'dictionary.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody DictionaryItemDto dictionaryItemDto) {
        dictionaryItemService.saveDictionaryItem(
            DtoConverter.toEntity(dictionaryItemDto, DictionaryItemMapStruct.INSTANCE)
        );
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('dictionary.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        dictionaryItemService.deleteDictionaryItem(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
