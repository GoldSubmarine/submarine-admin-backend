package com.htnova.system.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.DateUtil;
import com.htnova.system.workflow.dto.ActProcessDTO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ActProcessService {

    @Resource private RepositoryService repositoryService;
    @Resource private RuntimeService runtimeService;

    public IPage<ProcessDefinition> findActProcessList(
            ActProcessDTO actProcessDTO, IPage<ProcessDefinition> page) {
        // 获取查询流程定义对对象
        ProcessDefinitionQuery processDefinitionQuery =
                repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey();
        if (actProcessDTO.isLastVersion()) {
            processDefinitionQuery.latestVersion();
        }
        // 排序
        if (!CollectionUtils.isEmpty(page.orders())) {
            if (page.orders().get(0).isAsc()) {
                processDefinitionQuery.asc();
            } else {
                processDefinitionQuery.desc();
            }
        }
        // 动态查询
        if (StringUtils.isNotBlank(actProcessDTO.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike(
                    "%" + actProcessDTO.getCategory() + "%");
        }
        if (StringUtils.isNotBlank(actProcessDTO.getName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + actProcessDTO.getName() + "%");
        }
        if (StringUtils.isNotBlank(actProcessDTO.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike("%" + actProcessDTO.getKey() + "%");
        }
        long start = (page.getCurrent() - 1) * page.getSize();
        long end = start + page.getSize();
        List<ProcessDefinition> result = processDefinitionQuery.listPage((int) start, (int) end);
        page.setRecords(result);
        page.setTotal(processDefinitionQuery.count());
        return page;
    }

    public ActProcessDTO getActProcessById(String id) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(id);
        Deployment deployment =
                repositoryService
                        .createDeploymentQuery()
                        .deploymentId(processDefinition.getDeploymentId())
                        .singleResult();
        return new ActProcessDTO(
                processDefinition, DateUtil.converter(deployment.getDeploymentTime()));
    }

    public String getActProcessResource(String id, String resourceName) {
        ProcessDefinition processDefinition =
                repositoryService
                        .createProcessDefinitionQuery()
                        .processDefinitionId(id)
                        .singleResult();
        InputStream resourceStream =
                repositoryService.getResourceAsStream(
                        processDefinition.getDeploymentId(), resourceName);
        try {
            return IOUtils.toString(resourceStream, Charset.defaultCharset());
        } catch (IOException e) {
            throw new ServiceException(ResultStatus.RESOURCE_NOT_FOUND);
        }
    }

    @Transactional
    public void changeProcessStatus(String id, ActProcessDTO.SuspensionState suspensionState) {
        if (ActProcessDTO.SuspensionState.active.equals(suspensionState)) {
            repositoryService.activateProcessDefinitionById(id);
        } else if (ActProcessDTO.SuspensionState.suspended.equals(suspensionState)) {
            repositoryService.suspendProcessDefinitionById(id);
        }
    }

    /** 删除部署、流程定义 */
    @Transactional
    public void deleteActDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }
}
