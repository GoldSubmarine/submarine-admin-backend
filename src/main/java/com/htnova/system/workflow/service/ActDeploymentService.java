package com.htnova.system.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.util.DateUtil;
import com.htnova.system.workflow.dto.ActDeploymentDTO;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ActDeploymentService {

    @Resource private RepositoryService repositoryService;

    public IPage<Deployment> findActDeploymentList(
            ActDeploymentDTO actDeploymentDTO, IPage<Deployment> page) {
        // 获取查询流程定义对对象
        DeploymentQuery deploymentQuery =
                this.repositoryService.createDeploymentQuery().orderByDeploymentTime();
        // 排序
        if (!CollectionUtils.isEmpty(page.orders())) {
            if (page.orders().get(0).isAsc()) {
                deploymentQuery.asc();
            } else {
                deploymentQuery.desc();
            }
        }
        // 动态查询
        if (StringUtils.isNotBlank(actDeploymentDTO.getCategory())) {
            deploymentQuery.deploymentCategoryLike(actDeploymentDTO.getCategory());
        }
        if (StringUtils.isNotBlank(actDeploymentDTO.getName())) {
            deploymentQuery.deploymentNameLike(actDeploymentDTO.getName());
        }
        if (StringUtils.isNotBlank(actDeploymentDTO.getKey())) {
            deploymentQuery.deploymentKeyLike(actDeploymentDTO.getKey());
        }
        long start = (page.getCurrent() - 1) * page.getSize();
        long end = start + page.getSize();
        List<Deployment> result = deploymentQuery.listPage((int) start, (int) end);
        page.setRecords(result);
        page.setTotal(deploymentQuery.count());
        return page;
    }

    public ActDeploymentDTO getActDeploymentById(String id) {
        Deployment deployment =
                this.repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
        return ActDeploymentDTO.builder()
                .id(deployment.getId())
                .name(deployment.getName())
                .category(deployment.getCategory())
                .key(deployment.getKey())
                .deploymentTime(DateUtil.converter(deployment.getDeploymentTime()))
                .build();
    }

    /** 第一参数为流程定义id 第二参数为是否联级删除流程定义 如果第二参数为true的话所有有关这个流程定义的数据都会被删除 */
    public void deleteActDeployment(String deploymentId) {
        // 输入流程定义删除流程, 若该流程有正常运行的流程或者记录，会删除失败
        this.repositoryService.deleteDeployment(deploymentId, true);
    }
}
