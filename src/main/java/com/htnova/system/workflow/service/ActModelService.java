package com.htnova.system.workflow.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.system.workflow.dto.ActModelDTO;
import com.htnova.system.workflow.dto.ActModelExtraValue;
import com.htnova.system.workflow.dto.ActProcessDTO;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class ActModelService {

    @Resource private RepositoryService repositoryService;

    @Transactional
    public void deploy(String id) {
        ActModelDTO actModel = getActModelById(id);
        Deployment deployment =
                repositoryService
                        .createDeployment()
                        .name(actModel.getName())
                        .addString(
                                actModel.getName() + ActProcessDTO.BPMN_SUFFIX,
                                actModel.getEditorSourceValue())
                        .addString(
                                actModel.getName() + ActProcessDTO.PNG_NAME,
                                actModel.getEditorSourceExtraValue().getImg())
                        .category(actModel.getCategory())
                        .key(actModel.getKey())
                        .deploy();
        Model model = repositoryService.getModel(id);
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);

        // 设置流程分类
        List<ProcessDefinition> list =
                repositoryService
                        .createProcessDefinitionQuery()
                        .deploymentId(deployment.getId())
                        .list();
        for (ProcessDefinition processDefinition : list) {
            repositoryService.setProcessDefinitionCategory(
                    processDefinition.getId(), actModel.getCategory());
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(ResultStatus.DEFINITION_NOT_FOUND);
        }
    }

    public IPage<Model> findActModelList(ActModelDTO actModelDTO, IPage<Model> page) {
        // 获取查询流程定义对对象
        ModelQuery modelQuery = repositoryService.createModelQuery().orderByCreateTime();
        if (actModelDTO.isLastVersion()) {
            modelQuery.latestVersion();
        }
        if (Objects.nonNull(actModelDTO.getVersion())) {
            modelQuery.modelVersion(actModelDTO.getVersion());
        }
        // 排序
        if (!CollectionUtils.isEmpty(page.orders())) {
            if (page.orders().get(0).isAsc()) {
                modelQuery.asc();
            } else {
                modelQuery.desc();
            }
        }
        // 动态查询
        if (StringUtils.isNotBlank(actModelDTO.getCategory())) {
            modelQuery.modelCategoryLike("%" + actModelDTO.getCategory() + "%");
        }
        if (StringUtils.isNotBlank(actModelDTO.getName())) {
            modelQuery.modelNameLike("%" + actModelDTO.getName() + "%");
        }
        if (StringUtils.isNotBlank(actModelDTO.getKey())) {
            modelQuery.modelKey("%" + actModelDTO.getKey() + "%");
        }
        long start = (page.getCurrent() - 1) * page.getSize();
        long end = start + page.getSize();
        List<Model> result = modelQuery.listPage((int) start, (int) end);
        page.setRecords(result);
        page.setTotal(modelQuery.count());
        return page;
    }

    @Transactional
    public void save(ActModelDTO actModelDTO) {
        // 检测key是否重复
        Model lastModel =
                repositoryService
                        .createModelQuery()
                        .modelKey(actModelDTO.getKey())
                        .latestVersion()
                        .singleResult();
        if (StringUtils.isBlank(actModelDTO.getId()) && Objects.nonNull(lastModel)) {
            throw new ServiceException(ResultStatus.MODEL_KEY_DUPLICATE);
        }
        // 保存模型表
        Model model = repositoryService.newModel();
        model.setName(actModelDTO.getName());
        model.setCategory(actModelDTO.getCategory());
        model.setKey(actModelDTO.getKey());
        if (Objects.nonNull(lastModel)) {
            model.setVersion(lastModel.getVersion() + 1);
        }
        try {
            String metaInfoStr = new ObjectMapper().writeValueAsString(actModelDTO.getMetaInfo());
            model.setMetaInfo(metaInfoStr);
        } catch (Exception e) {
            log.error("序列化失败：", e);
        }
        repositoryService.saveModel(model);

        // 保存EditorSource数据
        repositoryService.addModelEditorSource(
                model.getId(), actModelDTO.getEditorSourceValue().getBytes());
        repositoryService.addModelEditorSourceExtra(
                model.getId(),
                JSONUtil.toJsonStr(actModelDTO.getEditorSourceExtraValue()).getBytes());
    }

    public ActModelDTO getActModelById(String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        byte[] modelEditorSource = repositoryService.getModelEditorSource(model.getId());
        byte[] modelEditorSourceExtra = repositoryService.getModelEditorSourceExtra(model.getId());
        ActModelDTO actModelDTO = new ActModelDTO(model);
        if (Objects.nonNull(modelEditorSource)) {
            actModelDTO.setEditorSourceValue(new String(modelEditorSource));
        }
        if (Objects.nonNull(modelEditorSourceExtra)) {
            actModelDTO.setEditorSourceExtraValue(
                    JSONUtil.toBean(new String(modelEditorSourceExtra), ActModelExtraValue.class));
        }
        return actModelDTO;
    }

    @Transactional
    public void deleteActModel(String modelId) {
        repositoryService.deleteModel(modelId);
    }
}
