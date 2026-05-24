package com.wbz.activiti.service;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveService {

    private static final Logger log = LoggerFactory.getLogger(LeaveService.class);

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final HistoryService historyService;

    public LeaveService(RuntimeService runtimeService,
                        TaskService taskService,
                        HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.historyService = historyService;
    }

    /**
     * 启动请假流程
     *
     * @param employee 申请人
     * @param manager  直属经理
     * @param hr       HR 审批人
     * @param days     请假天数
     * @param reason   请假原因
     */
    public ProcessInstance startLeaveProcess(String employee, String manager,
                                             String hr, int days, String reason) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", employee);
        variables.put("manager", manager);
        variables.put("hr", hr);
        variables.put("days", days);
        variables.put("reason", reason);
        variables.put("managerApproved", null);
        variables.put("hrApproved", null);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("leaveProcess", variables);
        log.info("启动请假流程 — 实例ID: {}, 申请人: {}, 天数: {}", pi.getId(), employee, days);
        return pi;
    }

    /**
     * 查询待办任务
     */
    public List<Task> getTasksByAssignee(String assignee) {
        return taskService.createTaskQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime().desc()
                .list();
    }

    /**
     * 完成审批任务
     *
     * @param taskId   任务ID
     * @param approved 是否批准
     * @param comment  审批意见
     */
    public void completeTask(String taskId, boolean approved, String comment) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new IllegalArgumentException("任务不存在: " + taskId);
        }

        String taskName = task.getName();
        String variableKey;
        if ("部门经理审批".equals(taskName)) {
            variableKey = "managerApproved";
        } else if ("HR审批".equals(taskName)) {
            variableKey = "hrApproved";
        } else {
            variableKey = "approved";
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put(variableKey, approved);
        variables.put(variableKey + "Comment", comment);

        taskService.complete(taskId, variables);
        log.info("任务完成 — ID: {}, 任务名: {}, 审批结果: {}", taskId, taskName, approved);
    }

    /**
     * 查询运行中的流程实例
     */
    public List<ProcessInstance> getRunningInstances() {
        return runtimeService.createProcessInstanceQuery()
                .orderByProcessInstanceId().desc()
                .list();
    }

    /**
     * 查询历史流程实例
     */
    public List<HistoricProcessInstance> getHistoryInstances() {
        return historyService.createHistoricProcessInstanceQuery()
                .orderByProcessInstanceStartTime().desc()
                .list();
    }

    /**
     * 获取流程实例的历史变量
     */
    public Map<String, Object> getHistoryVariables(String processInstanceId) {
        return historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list()
                .stream()
                .collect(HashMap::new,
                        (m, v) -> m.put(v.getVariableName(), v.getValue()),
                        HashMap::putAll);
    }
}
