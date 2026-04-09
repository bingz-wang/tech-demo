package com.wbz.springai.model;

import java.util.List;

public record LearningGuideResponse(
        String module,
        String currentMode,
        String description,
        List<String> lessons,
        List<String> practiceSteps
) {
}
