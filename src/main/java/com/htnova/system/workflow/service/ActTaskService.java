package com.htnova.system.workflow.service;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.htnova.common.dto.XPage;
import com.htnova.common.util.UserUtil;
import com.htnova.system.manage.entity.User;
import com.htnova.system.manage.service.UserService;
import com.htnova.system.workflow.dto.ActApplyDTO;
import com.htnova.system.workflow.dto.ActApplyDTO.ApplyStatus;
import com.htnova.system.workflow.dto.ActTaskDTO;
import com.htnova.system.workflow.dto.ProcessVariableDTO;
import com.htnova.system.workflow.dto.TaskVariableDTO;
import com.htnova.system.workflow.dto.TaskVariableDTO.ApproveType;
import com.htnova.system.workflow.mapper.ActMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActTaskService {

    @Resource private RepositoryService repositoryService;
    @Resource private TaskService taskService;
    @Resource private HistoryService historyService;
    @Resource private IdentityService identityService;
    @Resource private RuntimeService runtimeService;
    @Autowired private FormService formService;
    @Resource private UserService userService;
    @Resource private ActMapper actMapper;

    /** 获取待办列表 */
    public XPage<ActTaskDTO> getTodoList(
            ActTaskDTO actTaskDTO, String userId, XPage<ActTaskDTO> page) {
        // =============== 已经签收的任务  ===============
        TaskQuery todoTaskQuery =
                taskService
                        .createTaskQuery()
                        .taskAssignee(userId)
                        .active()
                        .includeProcessVariables()
                        .orderByTaskCreateTime()
                        .desc();
        // 设置查询条件
        if (StringUtils.isNotBlank(actTaskDTO.getProcessDefinitionKey())) {
            todoTaskQuery.processDefinitionKey(actTaskDTO.getProcessDefinitionKey());
        }
        long start = (page.getPageNum() - 1) * page.getPageSize();
        long end = start + page.getPageSize();
        List<Task> result = todoTaskQuery.listPage((int) start, (int) end);
        page.setData(result.stream().map(ActTaskDTO::new).collect(Collectors.toList()));
        page.setTotal(todoTaskQuery.count());
        return page;
    }

    /** 获取已办任务 */
    public XPage<ActTaskDTO> getDonePage(
            ActTaskDTO actTaskDTO, String userId, XPage<ActTaskDTO> page) {
        HistoricTaskInstanceQuery histTaskQuery =
                historyService
                        .createHistoricTaskInstanceQuery()
                        .taskAssignee(userId)
                        .finished()
                        .includeProcessVariables()
                        .orderByHistoricTaskInstanceEndTime()
                        .desc();

        // 设置查询条件
        if (StringUtils.isNotBlank(actTaskDTO.getProcessDefinitionKey())) {
            histTaskQuery.processDefinitionKey(actTaskDTO.getProcessDefinitionKey());
        }
        if (actTaskDTO.getBeginTime() != null) {
            histTaskQuery.taskCompletedAfter(actTaskDTO.getBeginTime());
        }
        if (actTaskDTO.getEndTime() != null) {
            histTaskQuery.taskCompletedBefore(actTaskDTO.getEndTime());
        }

        // 查询总数
        page.setTotal(histTaskQuery.count());

        long start = (page.getPageNum() - 1) * page.getPageSize();
        long end = start + page.getPageSize();
        // 查询列表
        List<HistoricTaskInstance> histList = histTaskQuery.listPage((int) start, (int) end);
        for (HistoricTaskInstance histTask : histList) {
            page.getData().add(new ActTaskDTO(histTask));
        }
        return page;
    }

    /** 获取自己的申请(流程实例查询) */
    public XPage<ActApplyDTO> getApplyPage(ActApplyDTO actApplyDTO, XPage<ActApplyDTO> page) {
        HistoricProcessInstanceQuery historicProcInsQuery =
                historyService.createHistoricProcessInstanceQuery();

        if (StringUtils.isNotBlank(actApplyDTO.getProcessInstanceId())) {
            historicProcInsQuery.processInstanceId(actApplyDTO.getProcessInstanceId());
        }
        if (StringUtils.isNotBlank(actApplyDTO.getProcessDefinitionId())) {
            historicProcInsQuery.processDefinitionId(actApplyDTO.getProcessDefinitionId());
        }
        if (StringUtils.isNotBlank(actApplyDTO.getProcessDefinitionKey())) {
            historicProcInsQuery.processDefinitionKey(actApplyDTO.getProcessDefinitionKey());
        }
        if (StringUtils.isNotBlank(actApplyDTO.getProcessDefinitionCategory())) {
            historicProcInsQuery.processDefinitionCategory(
                    actApplyDTO.getProcessDefinitionCategory());
        }
        if (StringUtils.isNotBlank(actApplyDTO.getProcessDefinitionName())) {
            historicProcInsQuery.processDefinitionName(actApplyDTO.getProcessDefinitionName());
        }
        if (Objects.nonNull(actApplyDTO.getProcessDefinitionVersion())) {
            historicProcInsQuery.processDefinitionVersion(
                    actApplyDTO.getProcessDefinitionVersion());
        }
        if (StringUtils.isNotBlank(actApplyDTO.getProcessInstanceBusinessKey())) {
            historicProcInsQuery.processInstanceBusinessKey(
                    actApplyDTO.getProcessInstanceBusinessKey());
        }
        if (StringUtils.isNotBlank(actApplyDTO.getStartedBy())) {
            historicProcInsQuery.startedBy(actApplyDTO.getStartedBy());
        }
        if (StringUtils.isNotBlank(actApplyDTO.getProcessInstanceName())) {
            historicProcInsQuery.processInstanceNameLike(actApplyDTO.getProcessInstanceName());
        }
        if (ApplyStatus.done.equals(actApplyDTO.getStatus())) {
            historicProcInsQuery.finished();
        }
        if (ApplyStatus.process.equals(actApplyDTO.getStatus())) {
            historicProcInsQuery.unfinished();
        }
        List<HistoricProcessInstance> historicProcessInstances =
                historicProcInsQuery.listPage((int) page.getStartIndex(), (int) page.getEndIndex());
        page.setTotal(historicProcInsQuery.count());
        page.setData(
                historicProcessInstances.stream()
                        .map(ActApplyDTO::new)
                        .collect(Collectors.toList()));
        return page;
    }

    /** 撤销申请,终止流程实例 */
    public void deleteProcessInstanceById(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

    /**
     * 提交任务, 并保存意见
     *
     * @param taskId 任务ID
     * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
     * @param comment 任务提交意见的内容
     * @param vars: task parameters
     */
    private void complete(
            String taskId, String procInsId, String comment, Map<String, Object> vars) {
        // 添加意见
        if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)) {
            taskService.addComment(taskId, procInsId, comment);
        }
        // 提交任务
        taskService.complete(taskId, vars);
    }

    /** 审批 */
    @Transactional
    public void complete(
            String taskId,
            String procInsId,
            String comment,
            ApproveType approveType,
            TaskVariableDTO vars) {
        if (Objects.isNull(vars)) {
            vars = new TaskVariableDTO();
        }
        vars.setStatus(approveType);
        complete(taskId, procInsId, comment, BeanUtil.beanToMap(vars));
    }

    /** 跳转申请实例到某一节点 */
    public void changeActState(String processInstanceId, String currentActId, String toActId) {
        runtimeService
                .createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdTo(currentActId, toActId)
                .changeState();
    }

    /**
     * 获取流转历史列表（不包含结束节点）
     *
     * @param procInsId 流程实例
     * @param endActId 结束活动节点名称，即 taskDefinitionKey
     */
    public List<ActTaskDTO> histoicFlowList(String procInsId, String endActId) {
        return histoicFlowList(procInsId, null, endActId);
    }

    public List<ActTaskDTO> histoicFlowList(String procInsId, String startActId, String endActId) {
        List<ActTaskDTO> actList = Lists.newArrayList();
        List<HistoricActivityInstance> list =
                historyService
                        .createHistoricActivityInstanceQuery()
                        .processInstanceId(procInsId)
                        .orderByHistoricActivityInstanceStartTime()
                        .asc()
                        .orderByHistoricActivityInstanceEndTime()
                        .asc()
                        .list();
        boolean start = false;
        Map<String, Integer> actMap = Maps.newHashMap();
        for (int i = 0; i < list.size(); i++) {
            HistoricActivityInstance histIns = list.get(i);
            // 过滤开始节点前的节点
            if (StringUtils.isNotBlank(startActId) && startActId.equals(histIns.getActivityId())) {
                start = true;
            }
            if (StringUtils.isNotBlank(startActId) && !start) {
                continue;
            }
            // 显示开始节点和结束节点，或执行人不为空的任务
            if (StringUtils.isBlank(histIns.getAssignee())
                    && !StringUtils.equalsAny(
                            histIns.getActivityType(), "startEvent", "endEvent")) {
                continue;
            }
            // 给节点增加一个序号
            Integer actNum = actMap.get(histIns.getActivityId());
            if (actNum == null) {
                actMap.put(histIns.getActivityId(), actMap.size());
            }

            // 过滤结束节点后的节点
            if (StringUtils.isNotBlank(endActId) && endActId.equals(histIns.getActivityId())) {
                boolean bl = false;
                // 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
                for (int j = i + 1; j < list.size(); j++) {
                    HistoricActivityInstance hi = list.get(j);
                    Integer actNumA = actMap.get(hi.getActivityId());
                    if ((actNumA != null && actNumA < actNum)
                            || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())) {
                        bl = true;
                    }
                }
                if (!bl) {
                    break;
                }
            }

            ActTaskDTO e = new ActTaskDTO();
            // 获取流程发起人名称
            if ("startEvent".equals(histIns.getActivityType())) {
                HistoricProcessInstance historicProcessInstance =
                        historyService
                                .createHistoricProcessInstanceQuery()
                                .processInstanceId(procInsId)
                                .singleResult();
                if (Objects.nonNull(historicProcessInstance)
                        && StringUtils.isNotBlank(historicProcessInstance.getStartUserId())) {
                    User user =
                            userService.getUserById(
                                    Long.parseLong(historicProcessInstance.getStartUserId()));
                    if (user != null) {
                        e.setAssigneeId(historicProcessInstance.getStartUserId());
                        e.setAssigneeName(user.getName());
                    }
                }
            }
            // 获取任务执行人名称
            if (StringUtils.isNotEmpty(histIns.getAssignee())) {
                User user = userService.getUserById(Long.parseLong(histIns.getAssignee()));
                if (user != null) {
                    e.setAssigneeId(histIns.getAssignee());
                    e.setAssigneeName(user.getName());
                }
            }
            // 获取意见评论内容
            if (StringUtils.isNotBlank(histIns.getTaskId())) {
                List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
                if (commentList.size() > 0) {
                    e.setComment(commentList.get(0).getFullMessage());
                }
            }
            e.setActivityType(histIns.getActivityType());
            e.setActivityId(histIns.getActivityId());
            e.setProcessInstanceId(histIns.getProcessInstanceId());
            e.setProcessDefinitionId(histIns.getProcessDefinitionId());
            e.setBeginTime(histIns.getStartTime());
            e.setEndTime(histIns.getEndTime());
            e.setDurationInMillis(histIns.getDurationInMillis());
            actList.add(e);
        }
        return actList;
    }

    /** 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY） */
    public String getFormKey(String procDefId, String taskDefKey) {
        if (StringUtils.isNotBlank(taskDefKey)) {
            String taskFormKey = formService.getTaskFormKey(procDefId, taskDefKey);
            if (StringUtils.isNotBlank(taskFormKey)) return taskFormKey;
        }
        return formService.getStartFormKey(procDefId);
    }

    @Transactional
    public ProcessInstance startProcess(String procDefId, String businessTable, String businessId) {
        return startProcess(procDefId, businessTable, businessId, new ProcessVariableDTO());
    }

    @Transactional
    public ProcessInstance startProcess(
            String procDefId,
            String businessTable,
            String businessId,
            ProcessVariableDTO processVariable) {
        if (Objects.isNull(processVariable)) {
            processVariable = new ProcessVariableDTO();
        }
        if (StringUtils.isAnyBlank(
                processVariable.getApplyUserId(), processVariable.getApplyUserName())) {
            processVariable.setApplyUserId(UserUtil.getAuthUser().getId().toString());
            processVariable.setApplyUserName(UserUtil.getAuthUser().getName());
        }
        if (StringUtils.isAnyBlank(
                processVariable.getProcessDefinitionId(),
                processVariable.getProcessDefinitionName())) {
            ProcessDefinition processDefinition =
                    repositoryService
                            .createProcessDefinitionQuery()
                            .processDefinitionId(procDefId)
                            .singleResult();
            processVariable.setProcessDefinitionId(processDefinition.getId());
            processVariable.setProcessDefinitionName(processDefinition.getName());
            processVariable.setProcessDefinitionName(processDefinition.getKey());
        }
        // 设置流程发起人，act_hi_procinst 表中中的START_USER_ID_字段
        identityService.setAuthenticatedUserId(processVariable.getApplyUserId());
        // 启动流程
        ProcessInstance procIns =
                runtimeService.startProcessInstanceById(
                        procDefId,
                        businessTable + ":" + businessId,
                        BeanUtil.beanToMap(processVariable));

        // 更新业务表流程实例ID
        actMapper.updateProcInsIdByBusinessId(businessTable, procIns.getId(), businessId);
        return procIns;
    }
}
