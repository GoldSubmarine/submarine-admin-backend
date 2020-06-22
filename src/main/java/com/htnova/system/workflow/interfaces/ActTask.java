package com.htnova.system.workflow.interfaces;

public interface ActTask {

    /** 启动流程时使用 */
    String getProcessDefinitionId();

    /** 自己业务表的一个字段 */
    String getProcessInstanceId();

    /** 当前节点的id */
    String getTaskId();

    /** 审批建议 */
    String getComment();

    /** 审批截图（base64） */
    String getImg();
}
