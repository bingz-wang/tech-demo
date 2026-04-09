package com.wbz.springai.controller;

import com.wbz.springai.model.ChatRequest;
import com.wbz.springai.model.ChatResponse;
import com.wbz.springai.model.LearningGuideResponse;
import com.wbz.springai.service.AiStudyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/springai")
public class SpringAiLearningController {

    private final AiStudyService aiStudyService;

    public SpringAiLearningController(AiStudyService aiStudyService) {
        this.aiStudyService = aiStudyService;
    }

    @GetMapping("/guide")
    public LearningGuideResponse guide() {
        return aiStudyService.learningGuide();
    }

    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
        return aiStudyService.answer(request.question());
    }

    @PostMapping("/prompt/teacher")
    public ChatResponse teacherMode(@Valid @RequestBody ChatRequest request) {
        return aiStudyService.answerAsTeacher(request.question());
    }
}
