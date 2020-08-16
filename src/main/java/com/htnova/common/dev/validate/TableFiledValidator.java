package com.htnova.common.dev.validate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import com.htnova.common.constant.GlobalConst;
import com.htnova.common.dev.config.DevValidator;
import io.github.classgraph.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

/**
 * 校验数据库表和实体的数量是否匹配，以及每个表和实体的字段名称是否匹配
 * 表字段和实体字段是否匹配 交给mybatis的校验机制去做，这里不做校验
 */
@Slf4j
public class TableFiledValidator implements DevValidator {
    @Autowired
    private DataSource dataSource;

    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        Map<String, List<String>> tableColumns = getTableColumns();
        Map<String, List<String>> entityFields = getEntityFields();

        log.warn(String.format("表一共 %s 张", tableColumns.keySet().size()));
        log.warn(
            String.format(
                "表比实体多：%s",
                Sets
                    .difference(tableColumns.keySet(), entityFields.keySet())
                    .stream()
                    .filter(a -> (!a.toString().startsWith("act_") && !a.toString().startsWith("flw_")))
                    .collect(Collectors.toSet())
            )
        );
        log.warn(String.format("实体比表多：%s", Sets.difference(entityFields.keySet(), tableColumns.keySet())));

        log.warn("====================================");
        entityFields.forEach(
            (key, values) -> {
                List<String> columns = tableColumns.get(key);
                Collection<String> subtract = Sets.difference(
                    new HashSet<>(values),
                    columns
                        .stream()
                        .map(a -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, a))
                        .collect(Collectors.toSet())
                );
                Collection<String> subtract1 = Sets.difference(
                    new HashSet<>(columns),
                    values
                        .stream()
                        .map(a -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, a))
                        .collect(Collectors.toSet())
                );

                if (!CollectionUtils.isEmpty(subtract) || !CollectionUtils.isEmpty(subtract1)) {
                    log.warn(String.format("表【%s】字段 , 实体比表多：%s, 表比实体多：%s", key, subtract, subtract1));
                }
            }
        );
    }

    private Map<String, List<String>> getEntityFields() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(GlobalConst.PACKAGE).scan()) {
            ClassInfoList dtoClassInfos = scanResult
                .getAllClasses()
                .filter(classInfo -> classInfo.hasAnnotation(TableName.class.getName()))
                .filter(classInfo -> !classInfo.isInnerClass());
            return dtoClassInfos
                .stream()
                .flatMap(
                    classInfo ->
                        classInfo
                            .getFieldInfo()
                            .stream()
                            .filter(
                                fieldInfo -> {
                                    AnnotationInfo annotationInfo = fieldInfo.getAnnotationInfo(
                                        TableField.class.getName()
                                    );
                                    return (
                                        !fieldInfo.isStatic() &&
                                        (
                                            annotationInfo == null ||
                                            !annotationInfo
                                                .getParameterValues()
                                                .getValue("exist")
                                                .toString()
                                                .equalsIgnoreCase("false")
                                        )
                                    );
                                }
                            )
                            .map(
                                fieldInfo ->
                                    Pair.of(
                                        classInfo
                                            .getAnnotationInfo(TableName.class.getName())
                                            .getParameterValues()
                                            .get("value")
                                            .getValue()
                                            .toString(),
                                        fieldInfo.getName()
                                    )
                            )
                )
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));
        }
    }

    @SneakyThrows
    private Map<String, List<String>> getTableColumns() {
        Map<String, List<String>> result = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet columns = metadata.getColumns(connection.getCatalog(), null, "%", "%");
            while (columns.next()) {
                String tableName = columns.getString(3);
                result.computeIfAbsent(tableName, k -> new ArrayList<>());
                result.get(tableName).add(columns.getString(4));
            }
        }
        return result;
    }
}
