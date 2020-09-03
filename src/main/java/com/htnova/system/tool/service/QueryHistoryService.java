package com.htnova.system.tool.service;

import com.htnova.system.tool.mapper.QueryHistoryMapper;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueryHistoryService {
    @Resource
    private QueryHistoryMapper queryHistoryMapper;

    @Transactional
    public List<String> queryHistory(String fieldName, String tableName, String value) {
        if (StringUtils.isNotBlank(value)) {
            return queryHistoryMapper.queryHistoryLike(fieldName, tableName, value);
        }
        return queryHistoryMapper.queryHistory(fieldName, tableName);
    }
}
