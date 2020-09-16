package com.htnova.system.tool.service;

import com.htnova.system.tool.entity.BizNumber;
import com.htnova.system.tool.mapper.BizNumberMapper;
import java.time.format.DateTimeFormatter;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BizNumberService {
    @Resource
    private BizNumberMapper bizNumberMapper;

    public String computeBizNumber(BizNumber entity) {
        // 设置单据号
        String day = DateTimeFormatter.ofPattern("yyyyMMdd").format(entity.getTime());
        String maxBusiNumber = bizNumberMapper.getMaxBizNumber(entity.getTableName(), entity.getTime());
        String serial = StringUtils.EMPTY;
        if (StringUtils.isBlank(maxBusiNumber)) {
            serial += "0001";
        } else {
            String serialNumber = String.valueOf(Integer.parseInt(StringUtils.substring(maxBusiNumber, -4)) + 1);
            serial += StringUtils.repeat("0", 4 - serialNumber.length()) + serialNumber;
        }
        return day + serial;
    }
}
