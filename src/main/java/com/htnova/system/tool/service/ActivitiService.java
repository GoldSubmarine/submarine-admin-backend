package com.htnova.system.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.tool.dto.ActivitiDeploymentDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ActivitiService {

    @Resource private RepositoryService repositoryService;

    public ActivitiDeploymentDTO deploy(ActivitiDeploymentDTO processDeploymentDTO) {
        Deployment deployment =
                this.repositoryService
                        .createDeployment()
                        .name(processDeploymentDTO.getName())
                        .addString(processDeploymentDTO.getName(), processDeploymentDTO.getXml())
                        .category(processDeploymentDTO.getCategory())
                        .key(processDeploymentDTO.getKey())
                        .deploy();
        return new ActivitiDeploymentDTO(deployment);
    }

    public IPage<ActivitiDeploymentDTO> findActivitiList(
            ActivitiDeploymentDTO activitiDeploymentDTO, IPage<ActivitiDeploymentDTO> page) {
        // 获取查询流程定义对对象
        DeploymentQuery deploymentQuery =
                this.repositoryService.createDeploymentQuery().orderByDeploymenTime();
        // 排序
        if (!CollectionUtils.isEmpty(page.orders())) {
            if (page.orders().get(0).isAsc()) {
                deploymentQuery.asc();
            } else {
                deploymentQuery.desc();
            }
        }
        // 动态查询
        if (StringUtils.isNotBlank(activitiDeploymentDTO.getCategory())) {
            deploymentQuery.deploymentCategoryLike(activitiDeploymentDTO.getCategory());
        }
        if (StringUtils.isNotBlank(activitiDeploymentDTO.getName())) {
            deploymentQuery.deploymentNameLike(activitiDeploymentDTO.getName());
        }
        if (StringUtils.isNotBlank(activitiDeploymentDTO.getKey())) {
            deploymentQuery.deploymentKeyLike(activitiDeploymentDTO.getKey());
        }
        long start = (page.getCurrent() - 1) * page.getSize();
        long end = start + page.getSize();
        List<ActivitiDeploymentDTO> result =
                deploymentQuery.listPage((int) start, (int) end).stream()
                        .map(ActivitiDeploymentDTO::new)
                        .collect(Collectors.toList());
        page.setRecords(result);
        page.setTotal(deploymentQuery.count());
        return page;
    }

    public ActivitiDeploymentDTO getActivitiDeploymentById(String id) {
        Deployment deployment =
                this.repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
        return new ActivitiDeploymentDTO(deployment);
    }

    /** 第一参数为流程定义id 第二参数为是否联级删除流程定义 如果第二参数为true的话所有有关这个流程定义的数据都会被删除 */
    public void deleteActiviti(String deploymentId) {
        // 输入流程定义删除流程, 若该流程有正常运行的流程或者记录，会删除失败
        this.repositoryService.deleteDeployment(deploymentId, true);
    }
}
