package com.htnova.system.workflow.interfaces;

public interface ActTask {

    /** 启动流程时使用 */
    String getProcessDefinitionId();

    /** 自己业务表的一个字段 */
    String getProcessInstanceId();
}
