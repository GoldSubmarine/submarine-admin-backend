package com.htnova.system.workflow.dto;

import com.htnova.common.util.DateUtil;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowable.engine.repository.Deployment;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActDeploymentDTO {

    // 数据库id
    private String id;

    // 流程定义名称
    private String name;

    // 部署的 xml
    private String xml;

    // 流程定义的类别
    private String category;

    // 流程定义的key
    private String key;

    // 部署的时间
    private LocalDateTime deploymentTime;

    public ActDeploymentDTO(Deployment deployment) {
        this.setId(deployment.getId());
        this.setName(deployment.getName());
        this.setCategory(deployment.getCategory());
        this.setKey(deployment.getKey());
        this.setDeploymentTime(DateUtil.converter(deployment.getDeploymentTime()));
    }
}
