package com.htnova.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.dto.DictionaryDto;
import com.htnova.system.entity.Dictionary;
import com.htnova.system.mapper.DictionaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictionaryService extends ServiceImpl<DictionaryMapper, Dictionary> {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Transactional(readOnly = true)
    public IPage<Dictionary> findDictionaryList(DictionaryDto dictionaryDto, IPage<Void> xPage) {
        return dictionaryMapper.findPage(xPage, dictionaryDto);
    }

    @Transactional(readOnly = true)
    public List<Dictionary> findDictionaryList(DictionaryDto dictionaryDto) {
        return dictionaryMapper.findList(dictionaryDto);
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
    }
}
