package com.wbz.springai.service;

import com.wbz.springai.model.ChatResponse;
import com.wbz.springai.model.LearningGuideResponse;

public interface AiStudyService {

    LearningGuideResponse learningGuide();

    ChatResponse answer(String question);

    ChatResponse answerAsTeacher(String question);
}
