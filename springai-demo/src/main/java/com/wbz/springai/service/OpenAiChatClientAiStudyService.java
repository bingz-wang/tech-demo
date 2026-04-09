package com.wbz.springai.service;

import com.wbz.springai.model.ChatResponse;
import com.wbz.springai.model.LearningGuideResponse;
import org.springframework.ai.chat.client.ChatClient;

import java.util.List;

public class OpenAiChatClientAiStudyService implements AiStudyService {

    private final ChatClient chatClient;

    public OpenAiChatClientAiStudyService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public LearningGuideResponse learningGuide() {
        return new LearningGuideResponse(
                "springai-demo",
                "openai",
                "当前已启用真实 Spring AI + OpenAI 模型，你可以直接通过接口体验提示词构造和模型响应。",
                List.of(
                        "观察 ChatClient 如何以 fluent API 组织 prompt",
                        "学习如何使用 system prompt 约束回答风格",
                        "体验通过 profile 切换 stub 模式和真实模型模式",
                        "把 Controller -> Service -> ChatClient 的调用链串起来理解"
                ),
                List.of(
                        "调用 POST /api/springai/chat 体验基础问答",
                        "调用 POST /api/springai/prompt/teacher 体验教师模式",
                        "修改系统提示词后再次调用，观察返回风格变化",
                        "继续往结构化输出、RAG、工具调用方向扩展"
                )
        );
    }

    @Override
    public ChatResponse answer(String question) {
        String answer = chatClient.prompt()
                .user(question)
                .call()
                .content();
        return new ChatResponse("openai", answer);
    }

    @Override
    public ChatResponse answerAsTeacher(String question) {
        String answer = chatClient.prompt()
                .system("""
                        你现在是 Spring AI 入门老师。
                        请用中文回答，并严格按以下顺序输出：
                        1. 一句话结论
                        2. 关键概念
                        3. 最小代码示例
                        4. 一个常见坑
                        """)
                .user(question)
                .call()
                .content();
        return new ChatResponse("openai-teacher", answer);
    }
}
