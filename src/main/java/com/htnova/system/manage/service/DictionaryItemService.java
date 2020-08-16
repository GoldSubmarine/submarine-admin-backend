package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.manage.dto.DictionaryItemDto;
import com.htnova.system.manage.entity.DictionaryItem;
import com.htnova.system.manage.mapper.DictionaryItemMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryItemService extends ServiceImpl<DictionaryItemMapper, DictionaryItem> {
    @Resource
    private DictionaryItemMapper dictionaryItemMapper;

    @Transactional(readOnly = true)
    public IPage<DictionaryItem> findDictionaryItemList(DictionaryItemDto dictionaryItemDto, IPage<Void> xPage) {
        return dictionaryItemMapper.findPage(xPage, dictionaryItemDto);
    }

    @Transactional(readOnly = true)
    public List<DictionaryItem> findDictionaryItemList(DictionaryItemDto dictionaryItemDto) {
        return dictionaryItemMapper.findList(dictionaryItemDto);
    }

    @Transactional
    public void saveDictionaryItem(DictionaryItem dictionaryItem) {
        super.saveOrUpdate(dictionaryItem);
    }

    @Transactional
    public DictionaryItem getDictionaryItemById(long id) {
        return dictionaryItemMapper.selectById(id);
    }

    @Transactional
    public void deleteDictionaryItem(Long id) {
        super.removeById(id);
    }
}
