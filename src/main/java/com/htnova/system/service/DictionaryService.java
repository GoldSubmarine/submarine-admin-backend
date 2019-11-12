package com.htnova.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.dto.XPage;
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
    public XPage<Dictionary> findDictionaryList(Dictionary dictionary, XPage xPage) {
        XPage<Dictionary> dictionaryXPage = dictionaryMapper.findPage(xPage, dictionary);
        return dictionaryXPage;
    }

    @Transactional(readOnly = true)
    public List<Dictionary> findDictionaryList(Dictionary dictionary) {
        return dictionaryMapper.findList(dictionary);
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
