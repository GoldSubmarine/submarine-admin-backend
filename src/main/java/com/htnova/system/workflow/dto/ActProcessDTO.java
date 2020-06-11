package com.htnova.system.workflow.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowable.engine.repository.ProcessDefinition;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActProcessDTO {

    public static final String BPMN_SUFFIX = ".bpmn20.xml";
    public static final String SVG_NAME = ".svg";

    // 数据库id
    private String id;
    private String rev;

    // 流程定义的类别
    private String category;

    /**
     * 流程定义名称，注意后缀
     *
     * @see org.flowable.engine.impl.bpmn.deployer.ResourceNameUtil.BPMN_RESOURCE_SUFFIXES
     */
    private String name;

    // 流程定义的key
    private String key;

    // 版本
    private int version;

    // 部署id
    private String deploymentId;

    // 资源名称
    private String resourceName;

    /**
     * 图形文件名
     *
     * @see org.flowable.engine.impl.bpmn.deployer.ResourceNameUtil.DIAGRAM_SUFFIXES
     */
    private String dgrmResourceName;

    // 描述
    private String description;
    private boolean hasStartFormKey;
    private boolean hasGraphicalNotation;

    /** @see org.flowable.common.engine.impl.db.SuspensionState */
    private SuspensionState suspensionState;

    private String tenantId;
    private String engineVersion;
    private String derivedFrom;
    private String derivedFromRoot;
    private int derivedVersion;

    // 部署的 xml
    private String xml;
    private LocalDateTime deploymentTime;

    // 只展示最新版本
    private boolean lastVersion;

    public ActProcessDTO(ProcessDefinition processDefinition, LocalDateTime deploymentTime) {
        this.setId(processDefinition.getId());
        this.setCategory(processDefinition.getCategory());
        this.setKey(processDefinition.getKey());
        this.setName(processDefinition.getName());
        this.setVersion(processDefinition.getVersion());
        this.setResourceName(processDefinition.getResourceName());
        this.setDgrmResourceName(processDefinition.getDiagramResourceName());
        this.setDeploymentId(processDefinition.getDeploymentId());
        this.setSuspensionState(
                processDefinition.isSuspended()
                        ? SuspensionState.suspended
                        : SuspensionState.active);
        this.setDeploymentTime(deploymentTime);
        this.setId(processDefinition.getId());
        this.setName(processDefinition.getName());
        this.setCategory(processDefinition.getCategory());
        this.setKey(processDefinition.getKey());
    }

    public enum SuspensionState {
        active,
        suspended,
    }
}
