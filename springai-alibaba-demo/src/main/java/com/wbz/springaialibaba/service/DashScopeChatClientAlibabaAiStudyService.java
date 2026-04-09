package com.wbz.springaialibaba.service;

import com.wbz.springaialibaba.model.AlibabaChatResponse;
import com.wbz.springaialibaba.model.AlibabaLearningGuideResponse;
import org.springframework.ai.chat.client.ChatClient;

import java.util.List;

public class DashScopeChatClientAlibabaAiStudyService implements AlibabaAiStudyService {

    private final ChatClient chatClient;

    public DashScopeChatClientAlibabaAiStudyService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public AlibabaLearningGuideResponse learningGuide() {
        return new AlibabaLearningGuideResponse(
                "springai-alibaba-demo",
                "dashscope",
                "dashscope",
                "当前已启用 Spring AI Alibaba + DashScope，你可以直接体验真实模型问答。",
                List.of(
                        "观察 Spring AI Alibaba 如何沿用 Spring AI 的 ChatClient 调用方式",
                        "学习 DashScope 模型配置与系统提示词的组合",
                        "对照 springai-demo 理解两者的共同点与扩展点",
                        "为后续学习 Agent Framework 或 Graph 做准备"
                ),
                List.of(
                        "调用 POST /api/springai-alibaba/chat 体验基础问答",
                        "调用 POST /api/springai-alibaba/compare 体验对比讲解",
                        "修改系统提示词后再次请求，观察输出差异",
                        "后续继续扩展成 Agent 或 Workflow 示例"
                )
        );
    }

    @Override
    public AlibabaChatResponse answer(String question) {
        String answer = chatClient.prompt()
                .user(question)
                .call()
                .content();
        return new AlibabaChatResponse("dashscope", "dashscope", answer);
    }

    @Override
    public AlibabaChatResponse compareWithSpringAi(String question) {
        String answer = chatClient.prompt()
                .system("""
                        你现在是 Spring AI Alibaba 入门老师。
                        请用中文回答，并按以下顺序输出：
                        1. 一句话结论
                        2. Spring AI 和 Spring AI Alibaba 的关系
                        3. 适合当前 demo 的最小代码理解路径
                        4. 一个学习建议
                        """)
                .user(question)
                .call()
                .content();
        return new AlibabaChatResponse("dashscope-compare", "dashscope", answer);
    }
}
