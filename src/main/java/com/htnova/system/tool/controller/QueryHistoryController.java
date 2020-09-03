package com.htnova.system.tool.controller;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.htnova.common.constant.GlobalConst;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.system.tool.annotation.QueryHistoryField;
import com.htnova.system.tool.service.QueryHistoryService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class QueryHistoryController {
    private final Map<String, List<String>> ALLOW_QUERY_MAP = new HashMap<>();

    @Resource
    private QueryHistoryService queryHistoryService;

    public QueryHistoryController() {
        // 扫描所有的实体，获取可以被记忆查询的表和字段
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(GlobalConst.PACKAGE).scan()) {
            String annotationValue = "value";
            scanResult
                .getAllClasses()
                .filter(classInfo -> classInfo.hasAnnotation(TableName.class.getName()))
                .filter(classInfo -> !classInfo.isInnerClass())
                .forEach(
                    classInfo -> {
                        List<String> fieldNameList = classInfo
                            .getFieldInfo()
                            .filter(
                                fieldInfo ->
                                    fieldInfo.getAnnotationInfo().getNames().contains(QueryHistoryField.class.getName())
                            )
                            .stream()
                            .map(
                                fieldInfo -> {
                                    String field = (String) fieldInfo
                                        .getAnnotationInfo(QueryHistoryField.class.getName())
                                        .getParameterValues()
                                        .getValue(annotationValue);
                                    if (StringUtils.isBlank(field)) {
                                        field = fieldInfo.getName();
                                    }
                                    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field);
                                }
                            )
                            .collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(fieldNameList)) {
                            String tableName = (String) classInfo
                                .getAnnotationInfo(TableName.class.getName())
                                .getParameterValues()
                                .getValue(annotationValue);
                            ALLOW_QUERY_MAP.put(tableName, fieldNameList);
                        }
                    }
                );
        }
    }

    /**
     * 记忆查询
     * @param field: 允许逗号拼接
     */
    @GetMapping("/history")
    public Map<String, List<String>> queryHistory(String table, String field, String value) {
        Map<String, List<String>> result = new HashMap<>();
        Lists
            .newArrayList(field.split(","))
            .forEach(
                fieldItem -> {
                    String fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldItem);
                    String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, table);
                    boolean isAllow = Optional
                        .ofNullable(ALLOW_QUERY_MAP.get(tableName))
                        .map(list -> list.contains(fieldName))
                        .orElse(false);
                    if (isAllow) {
                        List<String> wrapList = queryHistoryService
                            .queryHistory(fieldName, tableName, value)
                            .stream()
                            .filter(StringUtils::isNotBlank)
                            .collect(Collectors.toList());
                        result.put(fieldItem, wrapList);
                    } else {
                        throw new ServiceException(ResultStatus.FIELD_NOT_SUPPORT);
                    }
                }
            );
        return result;
    }
}
