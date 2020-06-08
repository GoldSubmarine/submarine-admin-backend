package com.htnova.system.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.system.workflow.dto.ActModelDTO;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class ActModelService {

    @Resource private RepositoryService repositoryService;

    public void deploy(String id) {
        ActModelDTO actModel = getActModelById(id);
        this.repositoryService
                .createDeployment()
                .name(actModel.getName())
                .addString(actModel.getName(), actModel.getEditorSourceValue())
                .category(actModel.getCategory())
                .key(actModel.getKey())
                .deploy();
    }

    public IPage<Model> findActModelList(ActModelDTO actModelDTO, IPage<Model> page) {
        // 获取查询流程定义对对象
        ModelQuery modelQuery =
                this.repositoryService.createModelQuery().latestVersion().orderByCreateTime();
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
            modelQuery.modelCategoryLike(actModelDTO.getCategory());
        }
        if (StringUtils.isNotBlank(actModelDTO.getName())) {
            modelQuery.modelNameLike(actModelDTO.getName());
        }
        if (StringUtils.isNotBlank(actModelDTO.getKey())) {
            modelQuery.modelKey(actModelDTO.getKey());
        }
        long start = (page.getCurrent() - 1) * page.getSize();
        long end = start + page.getSize();
        List<Model> result = modelQuery.listPage((int) start, (int) end);
        page.setRecords(result);
        page.setTotal(modelQuery.count());
        return page;
    }

    public void saveActModel(String modelId, String xml) {
        // 保存EditorSource数据
        repositoryService.addModelEditorSource(modelId, xml.getBytes());
        // 版本号加一
        Model model = repositoryService.getModel(modelId);
        model.setVersion(model.getVersion() + 1);
        repositoryService.saveModel(model);
    }

    public void save(ActModelDTO actModelDTO) {
        // 检测key是否重复
        long count = repositoryService.createModelQuery().modelKey(actModelDTO.getKey()).count();
        if (StringUtils.isBlank(actModelDTO.getId()) && count > 0) {
            throw new ServiceException(ResultStatus.MODEL_KEY_DUPLICATE);
        }
        // 保存模型表
        Model model = repositoryService.newModel();
        model.setName(actModelDTO.getName());
        model.setCategory(actModelDTO.getCategory());
        model.setKey(actModelDTO.getKey());
        model.setVersion(actModelDTO.getVersion() + 1);
        try {
            String metaInfoStr = new ObjectMapper().writeValueAsString(actModelDTO.getMetaInfo());
            model.setMetaInfo(metaInfoStr);
        } catch (Exception e) {
            log.error("序列化失败：", e);
        }
        repositoryService.saveModel(model);

        // 如果已经存在模型数据，就复制一份
        if (StringUtils.isNotBlank(actModelDTO.getId())) {
            byte[] modelEditorSource = repositoryService.getModelEditorSource(actModelDTO.getId());
            if (Objects.nonNull(modelEditorSource)) {
                repositoryService.addModelEditorSource(model.getId(), modelEditorSource);
            }
        }
    }

    public ActModelDTO getActModelById(String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        byte[] modelEditorSource = repositoryService.getModelEditorSource(model.getId());
        ActModelDTO actModelDTO = new ActModelDTO(model);
        if (Objects.nonNull(modelEditorSource)) {
            actModelDTO.setEditorSourceValue(new String(modelEditorSource));
        }
        return actModelDTO;
    }

    public void deleteActModel(String modelId) {
        this.repositoryService.deleteModel(modelId);
    }
}
