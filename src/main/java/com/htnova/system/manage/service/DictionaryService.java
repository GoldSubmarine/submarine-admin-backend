package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.manage.dto.DictionaryDto;
import com.htnova.system.manage.dto.DictionaryItemDto;
import com.htnova.system.manage.entity.Dictionary;
import com.htnova.system.manage.entity.DictionaryItem;
import com.htnova.system.manage.mapper.DictionaryMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryService extends ServiceImpl<DictionaryMapper, Dictionary> {
    @Resource
    private DictionaryMapper dictionaryMapper;

    @Resource
    private DictionaryItemService dictionaryItemService;

    @Transactional(readOnly = true)
    public IPage<Dictionary> findDictionaryList(DictionaryDto dictionaryDto, IPage<Void> xPage) {
        return dictionaryMapper.findPage(xPage, dictionaryDto);
    }

    @Transactional(readOnly = true)
    public List<Dictionary> findDictionaryList(DictionaryDto dictionaryDto) {
        List<Dictionary> list = dictionaryMapper.findList(dictionaryDto);
        Map<Long, List<DictionaryItem>> dictionaryItemMap = dictionaryItemService
            .findDictionaryItemList(new DictionaryItemDto())
            .stream()
            .collect(Collectors.groupingBy(DictionaryItem::getDictionaryId));
        list.forEach(item -> item.setDictionaryItemList(dictionaryItemMap.get(item.getId())));
        return list;
    }

    @Transactional
    public void saveDictionary(Dictionary dictionary) {
        super.saveOrUpdate(dictionary);
    }

    @Transactional
    public Dictionary getDictionaryById(long id) {
        return dictionaryMapper.selectById(id);
    }

    @Transactional
    public void deleteDictionary(Long id) {
        super.removeById(id);
        dictionaryItemService.remove(
            Wrappers.lambdaQuery(new DictionaryItem()).eq(DictionaryItem::getDictionaryId, id)
        );
    }
}
