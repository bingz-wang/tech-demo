package com.wbz.springaialibaba.service;

import com.wbz.springaialibaba.model.AlibabaChatResponse;
import com.wbz.springaialibaba.model.AlibabaLearningGuideResponse;

public interface AlibabaAiStudyService {

    AlibabaLearningGuideResponse learningGuide();

    AlibabaChatResponse answer(String question);

    AlibabaChatResponse compareWithSpringAi(String question);
}
