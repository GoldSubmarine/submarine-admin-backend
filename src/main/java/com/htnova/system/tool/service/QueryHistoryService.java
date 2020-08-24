package com.htnova.system.tool.service;

import com.htnova.system.tool.mapper.QueryHistoryMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueryHistoryService {
    @Resource
    private QueryHistoryMapper queryHistoryMapper;

    @Transactional
    public List<String> queryHistory(String fieldName, String tableName) {
        return queryHistoryMapper.queryHistory(fieldName, tableName);
    }
}
