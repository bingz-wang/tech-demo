package com.wbz.springaialibaba.model;

import java.util.List;

public record AlibabaLearningGuideResponse(
        String module,
        String currentMode,
        String provider,
        String description,
        List<String> lessons,
        List<String> practiceSteps
) {
}
