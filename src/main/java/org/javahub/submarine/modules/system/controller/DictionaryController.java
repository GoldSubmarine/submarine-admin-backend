package org.javahub.submarine.modules.system.controller;

import org.javahub.submarine.common.dto.Result;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.mapstruct.DictionaryMapStruct;
import org.javahub.submarine.modules.system.dto.DictionaryDto;
import org.javahub.submarine.modules.system.entity.Dictionary;
import org.javahub.submarine.modules.system.service.DictionaryService;
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
    public XPage<DictionaryDto> findListByPage(DictionaryDto dictionaryDto, XPage xPage) {
        XPage<Dictionary> dictionaryPage = dictionaryService.findDictionaryList(DictionaryDto.toEntity(dictionaryDto), xPage);
        return dictionaryPage.toDto(DictionaryMapStruct.class);
    }

    /**
     * 查询
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/list/all")
    public List<DictionaryDto> findList(DictionaryDto dictionaryDto) {
        List<Dictionary> dictionaryList = dictionaryService.findDictionaryList(DictionaryDto.toEntity(dictionaryDto));
        return CommonUtil.toDto(dictionaryList, DictionaryMapStruct.class);
    }

    /**
     * 详情
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.find')")
    @GetMapping("/detail")
    public DictionaryDto getById(long id) {
        Dictionary dictionary = dictionaryService.getDictionaryById(id);
        return DictionaryDto.toDto(dictionary);
    }

    /**
     * 保存
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.add', 'dictionary.edit')")
    @PostMapping("/save")
    public void save(DictionaryDto dictionaryDto) {
        dictionaryService.saveDictionary(DictionaryDto.toEntity(dictionaryDto));
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAnyAuthority('dictionary', 'dictionary.del')")
    @DeleteMapping("/del")
    public void delete(DictionaryDto dictionaryDto) {
        dictionaryService.deleteDictionary(dictionaryDto.getId());
    }
}
