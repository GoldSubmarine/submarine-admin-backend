package com.htnova.scaffold.modules.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.DictionaryItem;
import com.htnova.scaffold.modules.system.mapper.DictionaryItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictionaryItemService extends ServiceImpl<DictionaryItemMapper, DictionaryItem> {

    @Resource
    private DictionaryItemMapper dictionaryItemMapper;

    @Transactional(readOnly = true)
    public XPage<DictionaryItem> findDictionaryItemList(DictionaryItem dictionaryItem, XPage xPage) {
        XPage<DictionaryItem> dictionaryItemXPage = dictionaryItemMapper.findPage(xPage, dictionaryItem);
        return dictionaryItemXPage;
    }

    @Transactional(readOnly = true)
    public List<DictionaryItem> findDictionaryItemList(DictionaryItem dictionaryItem) {
        return dictionaryItemMapper.findList(dictionaryItem);
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
