package com.wbz.springaialibaba.controller;

import com.wbz.springaialibaba.model.AlibabaChatRequest;
import com.wbz.springaialibaba.model.AlibabaChatResponse;
import com.wbz.springaialibaba.model.AlibabaLearningGuideResponse;
import com.wbz.springaialibaba.service.AlibabaAiStudyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/springai-alibaba")
public class SpringAiAlibabaLearningController {

    private final AlibabaAiStudyService studyService;

    public SpringAiAlibabaLearningController(AlibabaAiStudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/guide")
    public AlibabaLearningGuideResponse guide() {
        return studyService.learningGuide();
    }

    @PostMapping("/chat")
    public AlibabaChatResponse chat(@Valid @RequestBody AlibabaChatRequest request) {
        return studyService.answer(request.question());
    }

    @PostMapping("/compare")
    public AlibabaChatResponse compare(@Valid @RequestBody AlibabaChatRequest request) {
        return studyService.compareWithSpringAi(request.question());
    }
}
