package com.wbz.springai.service;

import com.wbz.springai.model.ChatResponse;
import com.wbz.springai.model.LearningGuideResponse;

import java.util.List;

public class LocalStubAiStudyService implements AiStudyService {

    @Override
    public LearningGuideResponse learningGuide() {
        return new LearningGuideResponse(
                "springai-demo",
                "stub",
                "当前未启用真实大模型，本模块会先用本地学习助手帮助你理解 Spring AI 的基本结构和调用方式。",
                List.of(
                        "认识 Spring AI 的核心抽象：ChatModel、ChatClient、Prompt",
                        "学习如何借助 starter 完成自动装配",
                        "理解 system prompt、user prompt 和 fluent API 的配合",
                        "体验把 AI 能力封装进 Controller 和 Service 的项目结构"
                ),
                List.of(
                        "先请求 GET /api/springai/guide 查看学习路径",
                        "再调用 POST /api/springai/chat 提问：什么是 ChatClient",
                        "接着调用 POST /api/springai/prompt/teacher 观察不同提示词风格",
                        "最后配置 OPENAI_API_KEY 并启用 openai profile，切换到真实模型"
                )
        );
    }

    @Override
    public ChatResponse answer(String question) {
        return new ChatResponse("stub", """
                这是本地学习模式下的示例回答。

                你的问题是：%s

                建议你先抓住这 4 个点：
                1. Spring AI 把大模型调用封装成 Spring 风格的 API。
                2. 常见入口是注入 ChatClient.Builder，然后构建 ChatClient。
                3. 常见调用链是 chatClient.prompt().user("问题").call().content()。
                4. 真正接模型时，再补上 API Key、模型名和温度等参数。
                """.formatted(question));
    }

    @Override
    public ChatResponse answerAsTeacher(String question) {
        return new ChatResponse("stub-teacher", """
                下面我用老师模式回答你的问题：%s

                一句话结论：
                Spring AI 是让你以 Spring 风格接入大模型的应用框架。

                关键概念：
                你会频繁接触 ChatClient、Prompt、System Message、User Message。

                学习建议：
                先阅读本模块里的 Controller、Config、Service，再切换到 openai profile 看真实模型效果。
                """.formatted(question));
    }
}
