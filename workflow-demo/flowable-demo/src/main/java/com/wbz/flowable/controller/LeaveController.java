package com.wbz.flowable.controller;

import com.wbz.flowable.service.LeaveService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * 提交请假申请 / 启动流程
     */
    @PostMapping("/submit")
    public Map<String, Object> submitLeave(@RequestBody LeaveRequest request) {
        ProcessInstance pi = leaveService.startLeaveProcess(
                request.employee(),
                request.manager(),
                request.hr(),
                request.days(),
                request.reason());
        return Map.of("processInstanceId", pi.getId(), "message", "请假申请已提交");
    }

    /**
     * 查询某人的待办任务
     */
    @GetMapping("/tasks")
    public List<Map<String, Object>> getTasks(@RequestParam String assignee) {
        return leaveService.getTasksByAssignee(assignee).stream()
                .map(this::taskToMap)
                .toList();
    }

    /**
     * 完成审批任务
     */
    @PostMapping("/complete/{taskId}")
    public Map<String, Object> completeTask(@PathVariable String taskId,
                                            @RequestBody CompleteRequest request) {
        leaveService.completeTask(taskId, request.approved(), request.comment());
        return Map.of("message", "审批完成");
    }

    /**
     * 运行中的流程实例
     */
    @GetMapping("/instances")
    public List<Map<String, Object>> getInstances() {
        return leaveService.getRunningInstances().stream()
                .map(pi -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", pi.getId());
                    m.put("name", pi.getName());
                    m.put("startTime", pi.getStartTime() != null ? pi.getStartTime().toString() : null);
                    m.put("variables", pi.getProcessVariables());
                    return m;
                })
                .toList();
    }

    /**
     * 历史流程实例
     */
    @GetMapping("/history")
    public List<Map<String, Object>> getHistory() {
        return leaveService.getHistoryInstances().stream()
                .map(hi -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", hi.getId());
                    m.put("name", hi.getName());
                    m.put("startTime", hi.getStartTime() != null ? hi.getStartTime().toString() : null);
                    m.put("endTime", hi.getEndTime() != null ? hi.getEndTime().toString() : null);
                    m.put("variables", leaveService.getHistoryVariables(hi.getId()));
                    return m;
                })
                .toList();
    }

    private Map<String, Object> taskToMap(Task task) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", task.getId());
        m.put("name", task.getName());
        m.put("assignee", task.getAssignee());
        m.put("createTime", task.getCreateTime() != null ? task.getCreateTime().toString() : null);
        m.put("processInstanceId", task.getProcessInstanceId());
        return m;
    }

    // ---- DTO ----

    public record LeaveRequest(String employee, String manager, String hr, int days, String reason) {}

    public record CompleteRequest(boolean approved, String comment) {}
}
