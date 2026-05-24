package com.wbz.activiti.controller;

import com.wbz.activiti.service.LeaveService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * 启动请假流程
     */
    @PostMapping("/start")
    public Map<String, Object> startLeave(@RequestBody Map<String, Object> body) {
        String employee = (String) body.get("employee");
        String manager = (String) body.get("manager");
        int days = (int) body.getOrDefault("days", 1);
        String reason = (String) body.getOrDefault("reason", "个人原因");

        ProcessInstance instance = leaveService.startLeaveProcess(employee, manager, days, reason);
        return Map.of(
                "success", true,
                "processInstanceId", instance.getId(),
                "message", "请假流程已启动"
        );
    }

    /**
     * 查询待办任务
     */
    @GetMapping("/tasks")
    public List<Map<String, Object>> getTasks(@RequestParam String assignee) {
        return leaveService.getTasks(assignee);
    }

    /**
     * 完成任务（审批）
     */
    @PostMapping("/complete/{taskId}")
    public Map<String, Object> completeTask(
            @PathVariable String taskId,
            @RequestBody Map<String, Object> body) {
        boolean approved = (boolean) body.getOrDefault("approved", true);
        String comment = (String) body.getOrDefault("comment", "");
        leaveService.completeTask(taskId, approved, comment);
        return Map.of(
                "success", true,
                "taskId", taskId,
                "approved", approved
        );
    }

    /**
     * 查看所有运行中的流程实例
     */
    @GetMapping("/instances")
    public List<Map<String, Object>> getInstances() {
        return leaveService.getProcessInstances();
    }
}
