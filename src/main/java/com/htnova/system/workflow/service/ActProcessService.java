package com.htnova.system.workflow.service;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.XPage;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.DateUtil;
import com.htnova.system.workflow.dto.ActProcessDTO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.repository.EngineDeployment;
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

    public XPage<ActProcessDTO> findActProcessList(
            ActProcessDTO actProcessDTO, XPage<ActProcessDTO> page) {
        // 获取查询流程定义对对象
        ProcessDefinitionQuery processDefinitionQuery =
                repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey();
        if (actProcessDTO.isLastVersion()) {
            processDefinitionQuery.latestVersion();
        }
        // 排序
        if (!CollectionUtils.isEmpty(page.getOrders())) {
            if (page.getOrders().get(0).isAsc()) {
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
        List<ProcessDefinition> result =
                processDefinitionQuery.listPage(
                        (int) page.getStartIndex(), (int) page.getEndIndex());
        page.setTotal(processDefinitionQuery.count());

        // 部署时间查询
        Map<String, LocalDateTime> deployTimeMap =
                repositoryService.createDeploymentQuery()
                        .deploymentIds(
                                result.stream()
                                        .map(ProcessDefinition::getDeploymentId)
                                        .collect(Collectors.toList()))
                        .list().stream()
                        .collect(
                                Collectors.toMap(
                                        EngineDeployment::getId,
                                        item -> DateUtil.converter(item.getDeploymentTime()),
                                        (a, b) -> a));
        page.setData(
                result.stream()
                        .map(
                                item ->
                                        new ActProcessDTO(
                                                item, deployTimeMap.get(item.getDeploymentId())))
                        .collect(Collectors.toList()));
        return page;
    }

    public List<ProcessDefinition> findAllActProcessList() {
        return repositoryService
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionKey()
                .asc()
                .latestVersion()
                .active()
                .list();
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

    public String getActProcessResource(String processDefinitionId, String resourceName) {
        ProcessDefinition processDefinition =
                repositoryService
                        .createProcessDefinitionQuery()
                        .processDefinitionId(processDefinitionId)
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
