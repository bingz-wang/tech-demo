package com.wbz.activiti.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("leaveService")
public class LeaveService {

    private static final Logger log = LoggerFactory.getLogger(LeaveService.class);

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public LeaveService(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    /**
     * 启动请假流程
     */
    public ProcessInstance startLeaveProcess(String employee, String manager, int days, String reason) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", employee);
        variables.put("manager", manager);
        variables.put("days", days);
        variables.put("reason", reason);

        ProcessInstance instance = runtimeService.startProcessInstanceByKey("leave-process", variables);
        log.info("启动请假流程: processInstanceId={}, employee={}, days={}", instance.getId(), employee, days);
        return instance;
    }

    /**
     * 获取待办任务列表
     */
    public List<Map<String, Object>> getTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime().desc()
                .list();

        return tasks.stream().map(task -> {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("taskName", task.getName());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("createTime", task.getCreateTime());
            map.put("assignee", task.getAssignee());
            Map<String, Object> vars = taskService.getVariables(task.getId());
            map.put("variables", vars);
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 完成任务
     */
    public void completeTask(String taskId, boolean approved, String comment) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);
        variables.put("comment", comment);

        taskService.complete(taskId, variables);
        log.info("完成任务: taskId={}, approved={}", taskId, approved);
    }

    /**
     * 获取所有运行中的流程实例
     */
    public List<Map<String, Object>> getProcessInstances() {
        return runtimeService.createProcessInstanceQuery()
                .orderByProcessInstanceId().desc()
                .list()
                .stream()
                .map(pi -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("processInstanceId", pi.getId());
                    map.put("processDefinitionId", pi.getProcessDefinitionId());
                    map.put("activityId", pi.getActivityId());
                    map.put("variables", runtimeService.getVariables(pi.getId()));
                    return map;
                })
                .collect(Collectors.toList());
    }

    /**
     * BPMN ServiceTask 调用：HR记录
     */
    public void recordLeave(DelegateExecution execution) {
        String employee = (String) execution.getVariable("employee");
        Integer days = (Integer) execution.getVariable("days");
        log.info("[HR记录] 员工 {} 请假 {} 天已记录归档", employee, days);
    }

    /**
     * BPMN ServiceTask 调用：通知拒绝
     */
    public void notifyRejection(DelegateExecution execution) {
        String employee = (String) execution.getVariable("employee");
        String comment = (String) execution.getVariable("comment");
        log.info("[通知拒绝] 员工 {} 的请假申请被拒绝，原因: {}", employee, comment);
    }
}
