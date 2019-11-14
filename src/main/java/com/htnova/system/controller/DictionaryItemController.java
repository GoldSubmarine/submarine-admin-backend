package com.htnova.system.controller;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.util.CommonUtil;
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
@RequestMapping("/dictionaryItem")
public class DictionaryItemController {

    @Resource
    private DictionaryItemService dictionaryItemService;

    /**
     * 分页查询
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/list/page")
    public XPage<DictionaryItemDto> findListByPage(DictionaryItemDto dictionaryItemDto, XPage xPage) {
        XPage<DictionaryItem> dictionaryItemPage = dictionaryItemService.findDictionaryItemList(DictionaryItemDto.toEntity(dictionaryItemDto), xPage);
        return dictionaryItemPage.toDto(DictionaryItemMapStruct.class);
    }

    /**
     * 查询
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/list/all")
    public List<DictionaryItemDto> findList(DictionaryItemDto dictionaryItemDto) {
        List<DictionaryItem> dictionaryItemList = dictionaryItemService.findDictionaryItemList(DictionaryItemDto.toEntity(dictionaryItemDto));
        return CommonUtil.toDto(dictionaryItemList, DictionaryItemMapStruct.class);
    }

    /**
     * 详情
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/detail")
    public DictionaryItemDto getById(long id) {
        DictionaryItem dictionaryItem = dictionaryItemService.getDictionaryItemById(id);
        return DictionaryItemDto.toDto(dictionaryItem);
    }

    /**
     * 保存
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.add', 'dictionary.edit')")
    @PostMapping("/save")
    public Result save(DictionaryItemDto dictionaryItemDto) {
        dictionaryItemService.saveDictionaryItem(DictionaryItemDto.toEntity(dictionaryItemDto));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.del')")
    @DeleteMapping("/del")
    public Result delete(DictionaryItemDto dictionaryItemDto) {
        dictionaryItemService.deleteDictionaryItem(dictionaryItemDto.getId());
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
